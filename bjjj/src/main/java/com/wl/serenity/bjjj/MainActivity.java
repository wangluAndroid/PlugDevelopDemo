package com.wl.serenity.bjjj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wl.serenity.bjjj.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_plug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "插件中的文本被点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(that, SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv_startService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(that, OneService.class));
            }
        });
    }
}
