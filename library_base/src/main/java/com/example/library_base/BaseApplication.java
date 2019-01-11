package com.example.library_base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.library_base.httputil.HttpUtils;
import com.example.library_base.utils.DebugUtil;
import com.example.library_base.utils.LogUtil;

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        LogUtil.init();
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        //  CrashReport.initCrashReport(getApplicationContext(), "3977b2d86f", DebugUtil.DEBUG);

        initTextSize();
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}