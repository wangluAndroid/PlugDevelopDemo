package com.wl.serenity.bjjj;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wl.serenity.plugstandard.ServiceInterface;

/**
 * Created by serenitynanian on 2018/5/10.
 */

public class BaseService extends Service implements ServiceInterface{

    private Service that;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void attach(Service proxyService) {
        this.that = proxyService;
    }


    @Override
    public void onRebind(Intent intent) {
        that.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return that.onUnbind(intent);
    }


    @Override
    public void onCreate() {
        that.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return that.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        that.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        that.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        that.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        that.onTrimMemory(level);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        that.onTaskRemoved(rootIntent);
    }
}
