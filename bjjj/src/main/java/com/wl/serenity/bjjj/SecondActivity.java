package com.wl.serenity.bjjj;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by serenitynanian on 2018/5/9.
 */

public class SecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //that 和 this 都可以
//        TextView tv = new TextView(this);
        TextView tv = new TextView(that);
        tv.setText("---------This is SecondActivity------");
        setContentView(tv);

    }
}
