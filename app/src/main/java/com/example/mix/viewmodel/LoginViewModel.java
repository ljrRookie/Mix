package com.example.mix.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.library_base.bean.LoginBean;
import com.example.library_base.bean.WanAndroidBannerBean;
import com.example.library_base.httputil.HttpClient;
import com.example.library_base.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    public final ObservableField<String> username = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<WanAndroidBannerBean> getWanAndroidBanner() {
        final MutableLiveData<WanAndroidBannerBean> data = new MutableLiveData<>();
        HttpClient.Builder.getWanAndroidServer().getWanAndroidBanner()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onNext(WanAndroidBannerBean wanAndroidBannerBean) {
                        ArrayList<String> mBannerImages = new ArrayList<String>();
                        ArrayList<String> mBannerTitles = new ArrayList<String>();
                        if (wanAndroidBannerBean != null
                                && wanAndroidBannerBean.getData() != null
                                && wanAndroidBannerBean.getData().size() > 0) {
                            List<WanAndroidBannerBean.DataBean> result = wanAndroidBannerBean.getData();
                            if (result != null && result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    //获取所有图片
                                    mBannerImages.add(result.get(i).getImagePath());
                                    mBannerTitles.add(result.get(i).getTitle());
                                }
                                wanAndroidBannerBean.setmBannerImages(mBannerImages);
                                wanAndroidBannerBean.setmBannerTitles(mBannerTitles);
                                data.setValue(wanAndroidBannerBean);
                            }
                        } else {
                            data.setValue(null);
                        }
                    }
                });
        return data;
    }

    public MutableLiveData<Boolean> register() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        /*if (!verifyData()) {
            data.setValue(false);
            return data;
        }*/
        HttpClient.Builder.getWanAndroidServer()
                .register("androidLjr", "15622732935", "15622732935")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean != null && loginBean.getData() != null) {
                            data.setValue(true);
                        } else {
                            if (loginBean != null) {
                                ToastUtil.showToastLong(loginBean.getErrorMsg());
                            }
                            data.setValue(false);
                        }
                    }
                });
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(username.get())) {
            ToastUtil.showToast("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showToast("请输入密码");
            return false;
        }
        return true;
    }

    public MutableLiveData<Boolean> login() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getWanAndroidServer().login(username.get(), password.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        if (bean != null && bean.getData() != null) {

                            data.setValue(true);
                        } else {
                            if (bean != null) {
                                ToastUtil.showToastLong(bean.getErrorMsg());
                            }
                            data.setValue(false);
                        }
                    }
                });
        return data;
    }
}
