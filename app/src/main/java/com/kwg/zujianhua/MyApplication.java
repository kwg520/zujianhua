package com.kwg.zujianhua;

import android.app.Application;

import com.kwg.arouter.ARouter;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
