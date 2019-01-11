package com.example.mix.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.library_base.BaseActivity;
import com.example.mix.R;
import com.example.mix.databinding.ActivityLoginRegisterBinding;
import com.example.mix.viewmodel.LoginViewModel;


public class LoginActivity extends BaseActivity <LoginViewModel,ActivityLoginRegisterBinding>{

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login_register);
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
            finish();
        }
    }
}
