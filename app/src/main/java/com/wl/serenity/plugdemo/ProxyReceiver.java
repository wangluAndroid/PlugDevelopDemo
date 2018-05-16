package com.wl.serenity.plugdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wl.serenity.plugstandard.BroadcastReceiverInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by serenitynanian on 2018/5/16.
 */

public class ProxyReceiver extends BroadcastReceiver {
    private String className ;
    BroadcastReceiverInterface broadcastReceiverInterface;
    public ProxyReceiver(String name,Context context) {
        this.className = name ;
        try {
            Class<?> aClass = PlugManager.getInstances().getDexClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            broadcastReceiverInterface = (BroadcastReceiverInterface) constructor.newInstance(new Object[]{});

            broadcastReceiverInterface.attach(context);


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

    // class ---- object ------- receiverInterface
    @Override
    public void onReceive(Context context, Intent intent) {

        broadcastReceiverInterface.onReceive(context,intent);
    }
}
