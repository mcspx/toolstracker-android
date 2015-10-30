package com.mstack.toolstracker;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by layer on 23/10/2558.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
