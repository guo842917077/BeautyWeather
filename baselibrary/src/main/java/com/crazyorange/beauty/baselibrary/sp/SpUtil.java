package com.crazyorange.beauty.baselibrary.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.crazyorange.beauty.baselibrary.BuildConfig;
import com.crazyorange.beauty.baselibrary.log.ALog;

public class SpUtil {
    // 整个工具类没有考虑过懒加载，主要因为这个工具类在App启动后基本上会马上使用，所以懒加载的实际意义不大
    private final static String SHARED_PREFERENCES_NAME = "BeautyWeather";
    // 这里的App.getInstance()就是获取Application的实例
    private static SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        Context app = context.getApplicationContext();
        mSharedPreferences = app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    public static void applyValue(String key, Object value) {
        if (TextUtils.isEmpty(key)) return;
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value).apply();
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value).apply();
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value).apply();
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof String) {
            mEditor.putString(key, (String) value).apply();
        } else {
            try {

            } catch (Exception e) {
                if (BuildConfig.IsDebug) {
                    ALog.e("SpUtils", "SpUtil apply value error");
                }
            }
        }
    }

    public static void commitValue(String key, Object value) {
        if (TextUtils.isEmpty(key)) return;
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value).commit();
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof String) {
            mEditor.putString(key, (String) value).commit();
        } else {
            try {

            } catch (Exception e) {
                if (BuildConfig.IsDebug) {
                    ALog.e("SpUtils", "SpUitl commit value error");
                }
            }
        }
    }

    public static Object getValue(String key, Object defValue) {
        if (TextUtils.isEmpty(key)) return null;
        if (defValue instanceof Integer) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof String) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else {
            try {

            } catch (Exception e) {
                if (BuildConfig.IsDebug) {
                    ALog.e("SpUtils", "SpUitl get value error");
                }
            }
        }
        return null;
    }
}