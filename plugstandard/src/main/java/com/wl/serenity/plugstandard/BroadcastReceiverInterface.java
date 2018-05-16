package com.wl.serenity.plugstandard;

import android.content.Context;
import android.content.Intent;

/**
 * Created by serenitynanian on 2018/5/11.
 */

public interface BroadcastReceiverInterface {
    public void attach(Context context);

    void onReceive(Context context, Intent intent);
}
