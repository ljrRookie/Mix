package com.example.wan_android.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.library_base.bean.TypeBean;
import com.example.library_base.httputil.HttpClient;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TypeViewModel extends AndroidViewModel{
    public TypeViewModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<TypeBean> getType(){
        MutableLiveData<TypeBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(TypeBean typeBean) {
                        data.setValue(typeBean);
                    }
                });
        return data;
    }
}
