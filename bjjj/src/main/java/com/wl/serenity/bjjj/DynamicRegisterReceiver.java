package com.wl.serenity.bjjj;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wl.serenity.bjjj.base.BaseDynamicRegisterReceiver;

/**
 * Created by serenitynanian on 2018/5/11.
 */

public class DynamicRegisterReceiver extends BaseDynamicRegisterReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(that, "----插件中的广播------", Toast.LENGTH_SHORT).show();

    }
}
