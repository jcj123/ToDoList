package com.qs.jcj.addlistview.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by jcj on 16/5/9.
 */
public class MyApplication extends Application{
    private static MyApplication application;

    public MyApplication() {
        application = this;
    }

    public static Context getGlobalContent() {
        return application;
    }
}
