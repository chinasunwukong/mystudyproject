package com.example.myapplication;

import android.util.Log;

import dalvik.system.PathClassLoader;

public class MyPathClassLoader extends PathClassLoader {

    public MyPathClassLoader(String dexPath, String librarySearchPath, ClassLoader parent) {
        super(dexPath, librarySearchPath, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Log.e("showDexClassLoader",name);
        return super.loadClass(name);
    }
}
