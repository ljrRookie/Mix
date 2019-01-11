package com.example.library_base.httputil.interceptor;

import android.content.Context;

import com.example.library_base.httputil.utils.CheckNetwork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeadInterceptor implements Interceptor {

    private final Context context;
    public HttpHeadInterceptor(Context context) {
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("Accept", "application/json;versions=1");
        if (CheckNetwork.isNetworkConnected(context)) {
            int maxAge = 60;
            builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
        } else {
            int maxStale = 60 * 60 * 24 * 28;
            builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
        }
        return chain.proceed(builder.build());
    }
}
