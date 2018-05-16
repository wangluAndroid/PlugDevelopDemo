package com.wl.serenity.bjjj;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wl.serenity.bjjj.base.BaseActivity;

/**
 * Created by serenitynanian on 2018/5/9.
 */

public class SecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(that);
        tv.setText("---------This is SecondActivity------");
        setContentView(tv);


        //在插件中注册一个广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("This is plugin register broadcast");
        registerReceiver(new DynamicRegisterReceiver(), filter);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //发送广播
                Intent intent = new Intent();
                intent.setAction("This is plugin register broadcast");
                sendBroadcast(intent);
            }
        });

    }
}
