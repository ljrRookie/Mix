package com.example.wan_android.api;

import com.example.library_base.httputil.HttpUtils;
import com.example.library_base.httputil.utils.BuildFactory;
import com.example.wan_android.bean.HomeListBean;
import com.example.wan_android.bean.WanAndroidBannerBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WanAndroidHttpClient {
    class Builder {
        public static WanAndroidHttpClient getWanAndroidServer() {
            return BuildFactory.getInstance().create(WanAndroidHttpClient.class, HttpUtils.API_WAN_ANDROID);
        }
    }

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    Observable<WanAndroidBannerBean> getWanAndroidBanner();
    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Observable<HomeListBean> getHomeList(@Path("page") int page, @Query("cid") Integer cid);

}
