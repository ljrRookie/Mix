package com.example.library_base.httputil;


import com.example.library_base.bean.HomeListBean;
import com.example.library_base.bean.LoginBean;
import com.example.library_base.bean.NavBean;
import com.example.library_base.bean.TypeBean;
import com.example.library_base.bean.WanAndroidBannerBean;
import com.example.library_base.httputil.utils.BuildFactory;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpClient {
    class Builder {
        public static HttpClient getDouBanService() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_DOUBAN);
        }

        public static HttpClient getTingServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_TING);
        }

        public static HttpClient getGankIOServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_GANKIO);
        }

        public static HttpClient getFirServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_FIR);
        }

        public static HttpClient getWanAndroidServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_WAN_ANDROID);
        }

        public static HttpClient getQSBKServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_QSBK);
        }
    }
    /**
     * 玩安卓登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 玩安卓注册
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<LoginBean> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
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
    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<TypeBean> getType();
    /**
     * 导航数据
     */
    @GET("navi/json")
    Observable<NavBean> getNavJson();

    /**
     * 收藏文章列表
     *
     * @param page 页码
     */
    @GET("lg/collect/list/{page}/json")
    Observable<HomeListBean> getCollectList(@Path("page") int page);

    /**
     * 收藏本站文章，errorCode返回0为成功
     *
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    Observable<HomeListBean> collect(@Path("id") int id);

    /**
     * 取消收藏(首页文章列表)
     *
     * @param id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<HomeListBean> unCollectOrigin(@Path("id") int id);

    /**
     * 取消收藏，我的收藏页面（该页面包含自己录入的内容）
     *
     * @param id       文章id
     * @param originId 列表页下发，无则为-1
     *                 (代表的是你收藏之前的那篇文章本身的id；
     *                 但是收藏支持主动添加，这种情况下，没有originId则为-1)
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<HomeListBean> unCollect(@Path("id") int id, @Field("originId") int originId);

    /**
     * 收藏网址
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Observable<HomeListBean> collectUrl(@Field("name") String name, @Field("link") String link);

    /**
     * 编辑收藏网站
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Observable<HomeListBean> updateUrl(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏网站
     *
     * @param id 收藏网址id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Observable<HomeListBean> unCollectUrl(@Field("id") int id);


}
