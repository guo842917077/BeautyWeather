package com.crazyorange.beauty.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crazyorange.beauty.R;
import com.crazyorange.beauty.baselibrary.sp.SpUtil;

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
public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<String> mUserName;
    private MutableLiveData<String> mPassword;
    private String mKeyLastUser = getApplication().getResources().getString(R.string.key_last_user);
    private String mKeyLastPwd = getApplication().getResources().getString(R.string.key_last_pwd);

    public UserViewModel(Application application) {
        super(application);
        initialUserData();
    }

    private void initialUserData() {
        mUserName = new MutableLiveData<String>();
        mPassword = new MutableLiveData<String>();
        hasLastLogin();
    }

    private void hasLastLogin() {
        String lastName = (String) SpUtil.getValue(mKeyLastUser, "");
        String lastPwd = (String) SpUtil.getValue(mKeyLastPwd, "");
        boolean hasUname = lastName != null && !lastName.equals("");
        boolean hasPwd = lastPwd != null && !lastPwd.equals("");
        if (hasUname) {
            mUserName.setValue(lastName);
        } else {
            mUserName.setValue("");
        }

        if (hasPwd) {
            mPassword.setValue(lastPwd);
        } else {
            mPassword.setValue("");
        }
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

    /**
     * 保存最近登录的用户
     * todo 使用 DataBinding 在 xml 中 button 的点击事件绑定了该函数
     *
     * @param username 用户名
     * @param password 密码
     */
    public void saveLastLoginUser(String username, String password) {
        SpUtil.applyValue(getApplication().getResources().getString(R.string.key_last_user), username);
        SpUtil.applyValue(getApplication().getResources().getString(R.string.key_last_pwd), password);
    }
}
