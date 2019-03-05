package com.example.myapplication.utils;

import android.util.Log;

public class LogBuffer {

    private static final String tag="LogBuffer";
    private long ptr=0;
    private String logPath;
    private String bufferPath;
    private int  bufferSize;

    public LogBuffer(String bufferPath,int capacity,String logPath) {
        this.bufferPath=bufferPath;
        this.bufferSize=capacity;
        this.logPath=logPath;
        try {
            ptr=initNative(bufferPath,capacity,logPath);
        }catch (Exception e) {
            Log.e(tag,e.getMessage());
        }
    }

    public String getLogPath() {
        return logPath;
    }
    public String getBufferPath() {
        return bufferPath;
    }
    public int getBufferSize() {
        return bufferSize;
    }
    public void write(String log) {
        if (ptr != 0) {
            try {
                writeNative(ptr, log);
            }catch (Exception e) {
                Log.e(tag, e.getMessage());
            }
        }
    }
    public void flushAsync() {
        if (ptr != 0) {
            try {
                flushAsyncNative(ptr);
            }catch (Exception e) {
                Log.e(tag, e.getMessage());
            }
        }
    }
    public void release() {
        if (ptr != 0) {
            try {
                releaseNative(ptr);
            }catch (Exception e) {
                Log.e(tag,e.getMessage());
            }
            ptr = 0;
        }
    }
    static {
        System.loadLibrary("log4a-lib");
    }
    private native static long initNative(String bufferPath, int capacity, String logPath);
    private native void writeNative(long ptr, String log);
    private native void flushAsyncNative(long ptr);
    private native void releaseNative(long ptr);

}
