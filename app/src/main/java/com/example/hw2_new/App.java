package com.example.hw2_new;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferences.init(this);
    }
}
