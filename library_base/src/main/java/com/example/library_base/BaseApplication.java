package com.example.library_base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.alibaba.android.arouter.launcher.ARouter;
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
        if (DebugUtil.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(baseApplication); // 尽可能早，推荐在Application中初始化
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