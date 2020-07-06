package com.crazyorange.beauty.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author guojinlong
 * <p>
 * 登录 注册 用户的业务类
 * 1. 使用 ViewModel
 * 2. 使用 LiveData 监控声明周期
 * <p>
 * 使用 ViewModel,每一次 ViewModel 中的数据发生改变，仍然需要主动通知业务。
 * 使用 LiveData,当数据发生改变的时候，可以自动的更新 UI。LiveData 可以直接和 View 绑定
 */
public class UserViewModel extends ViewModel {
    private MutableLiveData<String> mUserName;
    private MutableLiveData<String> mPassword;


    public UserViewModel() {
        initialUserData();
    }

    private void initialUserData() {
        mUserName = new MutableLiveData<String>();
        mPassword = new MutableLiveData<String>();
        mPassword.setValue("");
        mUserName.setValue("");
    }


    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUserName(String username) {
        mUserName.setValue(username);
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        mPassword.setValue(password);
    }


    public MutableLiveData<String> getUserName() {
        return mUserName;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }
}
