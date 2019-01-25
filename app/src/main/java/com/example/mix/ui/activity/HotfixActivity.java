package com.example.mix.ui.activity;

import android.view.View;

import com.example.library_base.BaseActivity;
import com.example.library_base.utils.ToastUtil;
import com.example.library_base.viewmodel.NoViewModel;
import com.example.mix.R;
import com.example.mix.databinding.ActivityHotFixBinding;

public class HotfixActivity extends BaseActivity<NoViewModel,ActivityHotFixBinding>{

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("热修复测试");
    }

    public void bug(View view) {
        ToastUtil.showToast("bug");
    }

    public void fix(View view) {
        ToastUtil.showToast("fix");

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_hot_fix;
    }
}
