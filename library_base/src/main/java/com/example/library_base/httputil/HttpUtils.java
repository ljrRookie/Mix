package com.example.library_base.httputil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.library_base.httputil.interceptor.AddCacheInterceptor;
import com.example.library_base.httputil.interceptor.AddCookiesInterceptor;
import com.example.library_base.httputil.interceptor.HttpHeadInterceptor;
import com.example.library_base.httputil.interceptor.ReceivedCookiesInterceptor;
import com.example.library_base.utils.JsonUtil;
import com.example.library_base.utils.LogUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * 豆瓣api:
 * 问题：API限制为每分钟40次，一不小心就超了，马上KEY就被封,用不带KEY的API，每分钟只有可怜的10次。
 * 返回：code:112（rate_limit_exceeded2 IP 访问速度限制）
 * 解决：1.使用每分钟访问次数限制（客户端）2.更换ip (更换wifi)
 * 豆瓣开发者服务使用条款: https://developers.douban.com/wiki/?title=terms
 * 使用时请在"Application"下初始化。
 */

public class HttpUtils {
    private static HttpUtils instance;
    private Gson gson;
    private Context context;
    private IpmlTokenGetListener listener;
    private boolean debug;
    // gankio、豆瓣、（轮播图）
    public final static String API_GANKIO = "https://gank.io/api/";
    public final static String API_DOUBAN = "Https://api.douban.com/";
    public final static String API_TING = "https://tingapi.ting.baidu.com/v1/restserver/";
    public final static String API_FIR = "http://api.fir.im/apps/";
    public final static String API_WAN_ANDROID = "http://www.wanandroid.com/";
    public final static String API_QSBK = "http://m2.qiushibaike.com/";
    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
    public static int per_page_more = 20;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context, boolean debug) {
        this.context = context;
        this.debug = debug;
       // HttpHead.init(context);
    }
    public Retrofit.Builder getBuilder(String apiUrl){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);//设置远程地址
       // builder.addConverterFactory(new NullOnEmptyConverterFactory());
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder;
    }
    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }
    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }
    private OkHttpClient getOkClient() {
        OkHttpClient client1;
        client1 = getUnsafeOkHttpClient();
        return client1;
    }
    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Install the all-trusting trust manager TLS
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            //cache url
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            // 50 MiB
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.writeTimeout(30, TimeUnit.SECONDS);
          //  okBuilder.addInterceptor(new HttpHeadInterceptor(context));
            // 持久化cookie
          //  okBuilder.addInterceptor(new ReceivedCookiesInterceptor(context));
           // okBuilder.addInterceptor(new AddCookiesInterceptor(context));
            // 添加缓存，无网访问时会拿缓存,只会缓存get请求
            //okBuilder.addInterceptor(new AddCacheInterceptor(context));
           // okBuilder.cache(cache);
            okBuilder.addInterceptor(getInterceptor());

            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            StringBuilder mMessage = new StringBuilder();
            @Override
            public void log(@NonNull String message) {
                // 请求或者响应开始
                if (message.startsWith("--> POST")) {
                    mMessage.setLength(0);
                }
                // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
                if ((message.startsWith("{") && message.endsWith("}"))
                        || (message.startsWith("[") && message.endsWith("]"))) {
                    message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
                }
                mMessage.append(message.concat("\n"));
                // 响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    LogUtil.e(mMessage.toString());
                }
            }
        });
        if (debug) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 测试
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE); // 打包
        }
        return httpLoggingInterceptor;
    }
    final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }};
}
