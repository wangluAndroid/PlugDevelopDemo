package com.wl.serenity.bjjj;

import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by serenitynanian on 2018/5/10.
 */

public class OneService extends BaseService {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    @Override
    public void onCreate() {
        super.onCreate();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.i("wl", "---插件中的OneService--run: -----"+atomicInteger.getAndIncrement()+"-----Thread----->"+Thread.currentThread());
            }
        }, 2, 2, TimeUnit.SECONDS);
    }
}
