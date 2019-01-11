package com.example.baiting.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import dalvik.system.DexClassLoader;

public class BaseApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //replaceClassLoader();

    }

    private String parse() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(System.currentTimeMillis());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                    Log.e("thread","thread finish " + parse());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        Log.e("thread",Thread.currentThread().getName() + " start for " + parse());
        try {
            for (int i = 0; i < 20; i++) {
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e("thread",Thread.currentThread().getName() + " for finish " + parse());

        if (thread != null && thread.isAlive()) {
            try {
                thread.join();
                Log.e("thread",Thread.currentThread().getName() + " end " + parse());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void replaceClassLoader() {
        String packageName = getPackageName();
        try {
            // get activityThread
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass
                    .getMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // get mPackages
            Field mPackagesField = activityThreadClass.getField("mPackages");
            mPackagesField.setAccessible(true);
            ArrayMap mPackages = (ArrayMap) mPackagesField.get(currentActivityThread);


            // get DexClassLoader
            WeakReference wr = (WeakReference) mPackages.get(packageName);
            Class mClassLoaderClass = Class.forName("android.app.LoadedApk");
            Field mClassLoaderField = mClassLoaderClass.getField("mClassLoader");
            mClassLoaderField.setAccessible(true);
            DexClassLoader mClassLoader = (DexClassLoader) mClassLoaderField.get(wr.get());

            // create DexClassLoader
            String dexPath = getPackageCodePath();
            Log.e("replaceClassLoader", "dexPath: " + dexPath);
            //注意这个地方
            //取的是ApplicationInfo中的nativeLibraryDir
            String nativeLibraryDir = getApplicationInfo().nativeLibraryDir;
            Log.e("replaceClassLoader", "nativeLibraryDir: " + nativeLibraryDir);

            MyPathClassLoader dLoader = new MyPathClassLoader(dexPath, nativeLibraryDir, mClassLoader);

            // replace
            mClassLoaderField.set(wr.get(), dLoader);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
