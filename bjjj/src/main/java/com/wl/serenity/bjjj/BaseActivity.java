package com.wl.serenity.bjjj;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wl.serenity.plugstandard.ActivityInterface;

/**
 * Created by serenitynanian on 2018/5/4.
 */

public class BaseActivity extends AppCompatActivity implements ActivityInterface{

    protected Activity that ;


    @Override
    public void attach(Activity proxyActivity) {

        this.that = proxyActivity;

    }


    @Override
    public void setContentView(View view) {
        if (that != null) {
            that.setContentView(view);
        }else{
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        that.setContentView(layoutResID);
    }

    @Override
    public View findViewById(@IdRes int id) {
        return that.findViewById(id);
    }


    @Override
    public void startActivity(Intent intent) {
        that.startActivity(intent);
    }

    @Override
    public Intent getIntent() {
        return that.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        return that.getClassLoader();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return that.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return that.getApplicationInfo();
    }

    @Override
    public Window getWindow() {
        return that.getWindow();
    }

    @Override
    public WindowManager getWindowManager() {
        return that.getWindowManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }


}
