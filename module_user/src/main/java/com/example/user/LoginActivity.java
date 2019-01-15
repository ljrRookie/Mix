package com.example.user;


import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.library_base.BaseActivity;
import com.example.user.databinding.ActivityLoginBinding;
import com.example.user.viewmodel.LoginViewModel;


@Route(path = "/module_user/login" )
public class LoginActivity extends BaseActivity <LoginViewModel,ActivityLoginBinding>{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);
        setTitle("登录");
        showContentView();
        bindingView.setViewModel(viewModel);
        initAnim();
    }

    private void initAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.login_sun_rotate);
        bindingView.ivSun.startAnimation(animation);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bindingView.ivSun.clearAnimation();
    }

    public void register(View view){
        viewModel.register().observe(this,this::loadSuccess);
    }
    public void login(View view){
        viewModel.login().observe(this,this::loadSuccess);
    }

    /**
     * 注册或登录成功
     */
    public void loadSuccess(Boolean aBoolean) {
        if (aBoolean != null && aBoolean) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
