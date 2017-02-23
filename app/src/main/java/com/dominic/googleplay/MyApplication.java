package com.dominic.googleplay;

import android.app.Application;

import com.dominic.googleplay.network.HeiMaRetrofit;

/**
 * Created by Dominic on 2017/2/22.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HeiMaRetrofit.getInstance().init(getApplicationContext());
    }
}
