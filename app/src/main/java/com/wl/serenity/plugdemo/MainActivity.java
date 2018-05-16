package com.wl.serenity.plugdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView loadPlug;
    private TextView goToPlug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        loadClassLoader();

        loadPlug = (TextView) findViewById(R.id.loadPlug);
        goToPlug = (TextView) findViewById(R.id.goToPlug);

        loadPlug.setOnClickListener(this);
        goToPlug.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.loadPlug:
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                Log.e(TAG, "onClick: "+absolutePath);
                loadPlug();
//                String wanglu = getDir("wanglu", Context.MODE_PRIVATE).getAbsolutePath();
//                Log.e(TAG, "onClick: ------>"+wanglu);
                break ;
            case R.id.goToPlug:
                goToPlug();
                break ;
        }
    }

    /**
     * 跳转到插件中的类
     */
    private void goToPlug() {
        Intent intent = new Intent(this, ProxyActivity.class);
        //PlugManager.getInstances().packageInfo.activities[0].name 获取launcher的activity的全类名
        intent.putExtra("className", PlugManager.getInstances().packageInfo.activities[0].name);
        startActivity(intent);
    }

    /**
     * 加载插件
     * 将外部储存卡里面的apk copy 到内部储存
     * 拷贝到内部的原因？
     *      ----因为ClassLoader只能加载data/data/包名/xxx下的dex文件，这是系统决定的；
     */
    private void loadPlug() {
        File filesDir = this.getDir("plugin", Context.MODE_PRIVATE);
        String name = "plugin.apk";
        ///data/data/com.wl.serenity.plugdemo/app_plugin/plugin.apk
        String filePath = new File(filesDir,name).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        InputStream is = null ;
        FileOutputStream os = null ;

        try {
            //storage/emulated/0/plugin.apk
            Log.i(TAG, "加载插件 " + new File(Environment.getExternalStorageDirectory(),name).getAbsolutePath());
            is = new FileInputStream(new File(Environment.getExternalStorageDirectory(),name));
            os = new FileOutputStream(filePath);

            int len = 0 ;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer,0,len);
            }

            File f = new File(filePath);
            if (f.exists()) {
                Toast.makeText(this, "dex overWrite", Toast.LENGTH_SHORT).show();
            }

            //加载插件中的类
            PlugManager.getInstances().loadPath(this);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 05-04 10:10:35.981 17989-17989/com.wl.serenity.plugdemo E/MainActivity: loadClassLoader: -----1----->dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.wl.serenity.plugdemo-2/base.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_dependencies_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_0_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_1_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_2_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_3_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_4_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_5_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_6_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_7_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_8_apk.apk", zip file "/data/app/com.wl.serenity.plugdemo-2/split_lib_slice_9_apk.apk"],nativeLibraryDirectories=[/data/app/com.wl.serenity.plugdemo-2/lib/arm64, /vendor/lib64, /system/lib64]]]
     * 05-04 10:10:35.981 17989-17989/com.wl.serenity.plugdemo E/MainActivity: loadClassLoader: -----2----->java.lang.BootClassLoader@1272102
     *
     * 一个android应用启动后，至少要有两个classloader
     * BootClassLoader:是android启动时创建的classloader，用来加载系统启动时所需要的framework层的类；
     * PathClssLoader:是应用app启动时，用来加载app中dex文件中的类，同时app可能需要系统的部分类对象，故应用启动时，系统会将BootClassLoader传递给应用app
     *
     */
    private void loadClassLoader() {
        ClassLoader classLoader = getClassLoader();
        if (classLoader != null) {
            Log.e(TAG, "loadClassLoader: -----1----->"+classLoader.toString());
            if (classLoader.getParent() != null) {
                ClassLoader parentClassLoader = classLoader.getParent();
                Log.e(TAG, "loadClassLoader: -----2----->"+parentClassLoader.toString() );
            }
        }
    }
}
