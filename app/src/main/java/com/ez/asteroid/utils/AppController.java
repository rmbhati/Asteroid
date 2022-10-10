package com.ez.asteroid.utils;

import android.app.Application;

public class AppController extends Application {
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
