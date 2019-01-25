package com.example.wan_android.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.library_base.bean.NavBean;
import com.example.library_base.httputil.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NavViewModel extends AndroidViewModel {
    public NavViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<NavBean> getNavData(){
        MutableLiveData<NavBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getNavJson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NavBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(NavBean navBean) {
                        if (navBean != null
                                && navBean.getData() != null
                                && navBean.getData().size() > 0) {

                            data.setValue(navBean);
                        } else {
                            data.setValue(null);
                        }
                    }
                });
        return data;

    }
}
