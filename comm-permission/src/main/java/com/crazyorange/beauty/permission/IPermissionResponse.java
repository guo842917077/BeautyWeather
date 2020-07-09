package com.crazyorange.beauty.permission;

import java.util.List;

public interface IPermissionResponse {
    void onSuccess(List<String> permissions);

    void onFailure(List<String> permissions);
}
