package com.crazyorange.beauty.permission;

import android.content.Context;

import com.crazyorange.beauty.permission.andpermission.CallAndPermission;

/**
 * @author guojinlong
 * <p>
 * Request common permission
 */

public class RequestPermission implements IRequestPermissions {
    private static volatile RequestPermission instance = null;
    public static volatile CallAndPermission mPermissionInstance;

    public static RequestPermission instance() {
        if (instance == null) {
            synchronized (RequestPermission.class) {
                if (instance == null) {
                    instance = new RequestPermission();
                    mPermissionInstance = new CallAndPermission();
                }
            }
        }
        return instance;
    }


    @Override
    public void requestStorage(Context context, IPermissionResponse callback) {
        mPermissionInstance.requestStorage(context, callback);
    }

    @Override
    public void requestInternet(Context context, IPermissionResponse callback) {
        mPermissionInstance.requestInternet(context, callback);
    }
}
