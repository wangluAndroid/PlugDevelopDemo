package com.wl.serenity.plugdemo;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wl.serenity.plugstandard.ServiceInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by serenitynanian on 2018/5/10.
 */

public class ProxyService extends Service {

    ServiceInterface oneService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
        return null;
    }

    private void init(Intent intent) {
        String serviceName = intent.getStringExtra("serviceName");
        Log.i("wl", "init: ----serviceName-------->"+serviceName);
        try {
            Class<?> aClass = PlugManager.getInstances().getDexClassLoader().loadClass(serviceName);
            try {
                Constructor<?> constructor = aClass.getConstructor(new Class[]{});
                oneService = (ServiceInterface) constructor.newInstance(new Object[]{});

                oneService.attach(this);

                oneService.onCreate();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * ===============以下为了绑定 插件中的生命周期==============
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        oneService.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        oneService.onTrimMemory(level);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        oneService.onTaskRemoved(rootIntent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        oneService.onRebind(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        oneService.onLowMemory();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        if (oneService == null) {
            init(intent);
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        oneService.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        oneService.onUnbind(intent);
        return super.onUnbind(intent);
    }
}
