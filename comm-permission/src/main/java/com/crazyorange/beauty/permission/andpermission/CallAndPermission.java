package com.crazyorange.beauty.permission.andpermission;

import android.annotation.SuppressLint;
import android.content.Context;

import com.crazyorange.beauty.permission.IPermissionResponse;
import com.crazyorange.beauty.permission.IRequestPermissions;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class CallAndPermission implements IRequestPermissions {
    @Override
    public void requestStorage(Context context, final IPermissionResponse callback) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE)
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        callback.onFailure(data);
                    }
                }).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                callback.onSuccess(data);
            }
        }).start();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void requestInternet(Context context, final IPermissionResponse callback) {
        AndPermission.with(context)
                .runtime()
                .permission("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE")
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        callback.onFailure(data);
                    }
                }).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                callback.onSuccess(data);
            }
        }).start();
    }
}
