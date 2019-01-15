package com.example.library_base.viewmodel;

import com.example.library_base.bean.HomeListBean;
import com.example.library_base.httputil.HttpClient;
import com.example.library_base.listener.Navigator;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectModel {
    /**
     * 收藏
     */
    public void collect(int id, Navigator.OnCollectNavigator navigator){
        HttpClient.Builder.getWanAndroidServer().collect(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.onFailure();
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean != null && bean.getErrorCode() == 0) {
                            navigator.onSuccess();
                        } else {
                            navigator.onFailure();
                        }
                    }
                });
    }
    /**
     * @param isCollectList 是否是收藏列表
     * @param originId      如果是收藏列表的话就是原始文章的id，如果是站外文章就是-1
     * @param id            bean里的id
     */
    public void unCollect(boolean isCollectList, int id, int originId, Navigator.OnCollectNavigator navigator) {
        if (isCollectList) {
            unCollect(id, originId, navigator);
        } else {
            unCollect(id, navigator);
        }
    }
    /**
     * 取消收藏
     */
    private void unCollect(int id, Navigator.OnCollectNavigator navigator) {
        HttpClient.Builder.getWanAndroidServer().unCollectOrigin(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.onFailure();
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean != null && bean.getErrorCode() == 0) {
                            navigator.onSuccess();
                        } else {
                            navigator.onFailure();
                        }
                    }
                });
    }

    /**
     * 取消收藏，我的收藏页
     */
    private void unCollect(int id, int originId,Navigator.OnCollectNavigator navigator) {
       HttpClient.Builder.getWanAndroidServer().unCollect(id, originId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.onFailure();
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean != null && bean.getErrorCode() == 0) {
                            navigator.onSuccess();
                        } else {
                            navigator.onFailure();
                        }
                    }
                });
    }
    /**
     * 收藏url
     */
    public void collectUrl(String name, String link, Navigator.OnCollectNavigator navigator) {
        HttpClient.Builder.getWanAndroidServer().collectUrl(name, link)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.onFailure();
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean != null && bean.getErrorCode() == 0) {
                            navigator.onSuccess();
                        } else {
                            navigator.onFailure();
                        }
                    }
                });
    }

    /**
     * 取消收藏url
     */
    public void unCollectUrl(int id, Navigator.OnCollectNavigator navigator) {
        HttpClient.Builder.getWanAndroidServer().unCollectUrl(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        navigator.onFailure();
                    }

                    @Override
                    public void onNext(HomeListBean bean) {
                        if (bean != null && bean.getErrorCode() == 0) {
                            navigator.onSuccess();
                        } else {
                            navigator.onFailure();
                        }
                    }
                });
    }

}
