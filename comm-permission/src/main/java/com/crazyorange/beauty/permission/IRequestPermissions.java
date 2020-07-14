package com.crazyorange.beauty.permission;

import android.content.Context;

public interface IRequestPermissions {
    void requestStorage(Context context, IPermissionResponse callback);

    void requestInternet(Context context, IPermissionResponse callback);
}
