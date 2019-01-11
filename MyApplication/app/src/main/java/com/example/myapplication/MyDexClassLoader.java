package com.example.myapplication;

import android.util.Log;

import dalvik.system.DexClassLoader;

public class MyDexClassLoader extends DexClassLoader {

    private DexClassLoader parent;

    public MyDexClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);

    }



    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Log.e("showDexClassLoader",name);
        return super.loadClass(name);
    }
}
