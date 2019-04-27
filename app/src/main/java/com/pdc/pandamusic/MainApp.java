package com.pdc.pandamusic;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
