package com.wl.serenity.bjjj.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wl.serenity.plugstandard.BroadcastReceiverInterface;

/**
 * Created by serenitynanian on 2018/5/11.
 */

public class BaseDynamicRegisterReceiver extends BroadcastReceiver implements BroadcastReceiverInterface {

    protected Context that ;

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    @Override
    public void attach(Context context) {
        this.that = context ;
    }
}
