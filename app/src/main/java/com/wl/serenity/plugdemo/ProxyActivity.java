package com.wl.serenity.plugdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wl.serenity.plugstandard.ActivityInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by serenitynanian on 2018/5/4.
 */

public class ProxyActivity extends AppCompatActivity  {

    //需要加载的类型--全类名
    private String className;

    private ActivityInterface activityInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        className = getIntent().getStringExtra("className");
        ComponentName component = getIntent().getComponent();
        /**
         * 05-04 19:26:19.491 12961-12961/com.wl.serenity.plugdemo E/ProxyActivity: onCreate: com.wl.serenity.plugdemo.ProxyActivity
         * 05-04 19:26:19.491 12961-12961/com.wl.serenity.plugdemo E/ProxyActivity: onCreate: com.wl.serenity.plugdemo
         * 05-04 19:26:19.491 12961-12961/com.wl.serenity.plugdemo E/ProxyActivity: onCreate: .ProxyActivity
         * 05-04 19:26:19.491 12961-12961/com.wl.serenity.plugdemo E/ProxyActivity: onCreate: class android.content.ComponentName
         */
        Log.e("ProxyActivity", "onCreate: "+component.getClassName());
        Log.e("ProxyActivity", "onCreate: "+component.getPackageName());
        Log.e("ProxyActivity", "onCreate: "+component.getShortClassName());
        Log.e("ProxyActivity", "onCreate: "+component.getClass().toString());

        //拿到class类型
        //反射不能够拿到---插件没有被安装，反射是拿不到的；只能通过classLoader来装载
        ClassLoader classLoader = getClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object obj = constructor.newInstance(new Object[]{});
            activityInterface = (ActivityInterface) obj;

            //注入上下文 --- 将ProxyActivity对象传递过去
            activityInterface.attach(this);

            //传值
            Bundle bundle = new Bundle();

            //调用生命周期函数
            activityInterface.onCreate(bundle);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startActivity(Intent intent) {
        String className = intent.getComponent().getClassName();
        Intent intent1 = new Intent(this, ProxyActivity.class);
        intent1.putExtra("className", className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intent = new Intent(this, ProxyService.class);
        intent.putExtra("serviceName", service.getComponent().getClassName());
        return super.startService(intent);
    }

    /**
     * 加载一个未安装的activity，必须通过以下两个方法加载类和资源
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return PlugManager.getInstances().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PlugManager.getInstances().getResources();
    }


    @Override
    protected void onStart() {
        super.onStart();
        activityInterface.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityInterface.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityInterface.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityInterface.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInterface.onDestroy();
    }
}
