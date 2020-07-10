package com.crazyorange.beauty.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.crazyorange.beauty.R;
import com.crazyorange.beauty.baselibrary.encrypted.Md5Util;
import com.crazyorange.beauty.baselibrary.math.RandomUtil;
import com.crazyorange.beauty.baselibrary.sp.SpUtil;
import com.crazyorange.beauty.database.BeautyDBManager;
import com.crazyorange.beauty.database.user.UserEntity;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private MutableLiveData<UserEntity> mUserEntity;
    private String mKeyLastUser = getApplication().getResources().getString(R.string.key_last_user);
    private String mKeyLastPwd = getApplication().getResources().getString(R.string.key_last_pwd);
    private FutureTask<Boolean> mQueryUserTask;
    private FutureTask<Boolean> mOccupiedTask;
    private FutureTask<Boolean> mRegisterTask;
    private ExecutorService mTaskExecutor;


    public UserViewModel(Application application) {
        super(application);
        mTaskExecutor = Executors.newSingleThreadExecutor();
        mQueryUserTask = new FutureTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return checkIsUserRegistered(mUserName.getValue(), mPassword.getValue());
            }

        });
        mOccupiedTask = new FutureTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return checkAccountOccupied(mUserEntity.getValue().getUsername());
            }
        });
        mRegisterTask = new FutureTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                registerUser(mUserEntity.getValue());
                return checkIsUserRegistered(mUserEntity.getValue().getUsername(), mUserEntity.getValue().getPassword());
            }
        });
        initialUserData();
    }

    private void initialUserData() {
        mUserEntity = new MutableLiveData<>();
        if (mUserEntity.getValue() == null) {
            mUserEntity.setValue(new UserEntity());
        }
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

    private boolean checkIsUserRegistered(String username, String password) {
        return BeautyDBManager.instance(getApplication()).getUserDao().queryUser(username, password) > 0;
    }

    private boolean checkAccountOccupied(String username) {
        return BeautyDBManager.instance(getApplication()).getUserDao().queryUserByName(username) > 0;
    }

    private void registerUser(UserEntity user) {
        BeautyDBManager.instance(getApplication()).getUserDao().insertUser(user);
    }


    public boolean isUserRegistered(String username, String password) {
        setUserName(username);
        setPassword(password);
        mTaskExecutor.submit(mQueryUserTask);
        try {
            return mQueryUserTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isAccountOccupied(String username) {
        mUserEntity.getValue().setUsername(username);
        mTaskExecutor.submit(mOccupiedTask);
        try {
            return mOccupiedTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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


    public boolean registerNewUser(String username, String password) {
        updateUserEntity(username, password, generatorUID());
        mTaskExecutor.submit(mRegisterTask);
        try {
            return mRegisterTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private MutableLiveData<UserEntity> updateUserEntity(String username, String password, String uid) {
        mUserEntity.getValue().setUsername(username);
        mUserEntity.getValue().setPassword(password);
        mUserEntity.getValue().setUid(uid);
        mUserEntity.getValue().setDatetime(new Date(System.currentTimeMillis()));
        return mUserEntity;
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


    public String generatorUID() {
        String uid = RandomUtil.generateChatAndNumberIdentifyCode(10);
        uid = Md5Util.convertMD5(uid);
        return uid;
    }
}
