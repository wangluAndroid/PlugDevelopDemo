package com.wl.serenity.plugdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by serenitynanian on 2018/5/4.
 *
 * 主要用来加载dex 和 类中的Resource
 */

public class PlugManager {

    protected PackageInfo packageInfo;

    private Resources resources ;
    private DexClassLoader dexClassLoader ;

    private PlugManager(){}

    //饿汉式--创建单例
    private static final PlugManager instances = new PlugManager();

    public static PlugManager getInstances(){
        return instances ;
    }

    /**
     * 加载插件中对应的类和类中的Resources
     * @param context
     */
    public void loadPath(Context context) {
        //加载那个插件  需要路径
        File filesDir = context.getDir("plugin", Context.MODE_PRIVATE);
        String name = "plugin.apk";
        String filePath = new File(filesDir,name).getAbsolutePath();

        Log.e("PlugManager", "loadPath: "+filePath);

        /**
         * 获取插件中的所有在AndroidManifest中注册过的activity；
         * packageInfo.activites[0]得到就是Action为LaunchActivity的ActivityInfo--插件中的MainActivity,
         *          可以从ActivityInfo中拿到全类名；
         *
         */
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(filePath, packageManager.GET_ACTIVITIES);
//        ActivityInfo[] activities = packageInfo.activities;
//        Log.e("PlugManager", "length: "+activities.length);
//        for (ActivityInfo activity : activities) {
//            Log.e("PlugManager", "length: "+activity.name);
//            Log.e("PlugManager", "length: "+activity.packageName);
//        }
        // 找到需要加载的那个activity name
        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);//dex的缓存路径
        dexClassLoader = new DexClassLoader(filePath, dexOutFile.getAbsolutePath(), null, context.getClassLoader());

        /**
         *  DexClassLoader 优化缓存目录不能使用外部储存，否则报出下面的错误
         *  java.lang.IllegalArgumentException: Optimized data directory /storage/emulated/0/dex is not owned by the current user.
         *  Shared storage cannot protect your application from code injection attacks.
         *
         *  DexClassLoader可以加载任意路径下的apk-----> dex  --->.class
         *
         */
//        File ff = new File(Environment.getExternalStorageDirectory(), "dex");
//        if(!ff.exists()){
//            ff.mkdirs();
//        }
//        dexClassLoader = new DexClassLoader(filePath, ff.getAbsolutePath(), null, context.getClassLoader());

        /**
         *  PathClassloader 只能加载系统注入的、已经安装的app
         */
//        //要么使用PathClassLoader
//        dexClassLoader = new PathClassLoader(filePath,context.getClassLoader());


        //加载插件apk中的资源文件
        try {
            /**
             * 1.从调用的构造函数参数来说，Class.newInstance只能调用无参构造函数，Constructor.newInstance则无此限制，
             * 原因通过Class类的getDeclaredConstructor(Class<?>... parameterTypes)方法就可以知道

             * 2.从调用的构造函数的可视性来说，Class.newInstance只能调用public类型的构造函数(不能调用内部类，
             * 会抛出java.lang.ClassNotFoundException异常)，Constructor.newInstance在某些情况下可以调用private类型的构造函数
             *
             */
            Class<AssetManager> clazz = AssetManager.class;
            AssetManager assetManager = clazz.newInstance();//只能使用无参的构造器
            Method addAssetPath = clazz.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, filePath);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }
}
