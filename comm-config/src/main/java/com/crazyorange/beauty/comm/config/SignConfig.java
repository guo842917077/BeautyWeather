package com.crazyorange.beauty.comm.config;

public class SignConfig {
    static {
        System.loadLibrary("CommConfig");
    }

    public static native String getApiKey();
}
