package com.crazyorange.beauty.permission;

import android.content.Context;

import com.crazyorange.beauty.permission.andpermission.CallAndPermission;

/**
 * @author guojinlong
 * <p>
 * Request common permission
 */

public class RequestPermission implements IRequestPermissions {
    public static volatile RequestPermission instance = null;
    public CallAndPermission mPermissionInstance;

    public RequestPermission instance() {
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
}
