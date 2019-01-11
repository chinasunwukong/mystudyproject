package com.example.myapplication.utils;


import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Constructor;


/**
 * 作者：zhuhf
 * 链接：https://www.jianshu.com/p/9e34defcb76f
 * 來源：简书
 * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
 */

public class InjectHelper {
    public static void inject(Activity host) {
        // 1、
        String classFullName = host.getClass().getName() + "$$ViewInjector";
        Log.e("InhjectHelper",classFullName);
        try {
            // 2、
            Class proxy = Class.forName(classFullName);
            // 3、
            Constructor constructor = proxy.getConstructor(host.getClass());
            // 4、
            constructor.newInstance(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

