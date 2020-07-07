package com.crazyorange.beauty.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.crazyorange.beauty.R;
import com.crazyorange.beauty.databinding.ActivityLoginBinding;
import com.crazyorange.beauty.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author crazyorange
 * <p>
 * login page
 * 1. use Lottie animation
 * 2. use MotionLayout
 * 3. use Room database
 * 4. use ARouter
 * 5. 使用 Databinding
 */
public class LoginActivity extends AppCompatActivity {
    private UserViewModel mUserVM;
    /**
     * 1.在 module 的 gradle 中开启 DataBinding 的支持会使用 APT 自动生成代码
     * 2.DataBinding 对象会持有所有布局中的控件
     */
    private ActivityLoginBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContentView();
        bindLifeCycle();
        mUserVM = createViewModel();

        /**
         * 在布局中声明了一个 user 变量,需要在 DataBinding 中绑定对应的对象
         */
        bindViewModel();
        registerListener();

    }

    public void startOverAnimation() {
        mDataBinding.imgOver.setSpeed(0.6f);
        mDataBinding.imgOver.playAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startOverAnimation();
    }


    private void registerListener() {
        mUserVM.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String password) {
                mDataBinding.etPwd.setText(password);
            }
        });
        mUserVM.getUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String username) {
                mDataBinding.etUname.setText(username);
            }
        });

        mDataBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mDataBinding.etUname.getText().toString();
                String password = mDataBinding.etPwd.getText().toString();

                if (mUserVM.isUserRegistered(mUserVM.getUserName().getValue(), mUserVM.getPassword().getValue())) {
                    mUserVM.saveLastLoginUser(username, password);
                } else {
                    String toast = getResources().getString(R.string.not_registered);
                    showSnackToast(toast);
                }
            }
        });
    }

    public void showSnackToast(String message) {
        Snackbar.make(mDataBinding.constraintLayout2, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.red_login_err))
                .show();

    }


    private void bindViewModel() {
        mDataBinding.setUser(mUserVM);
    }

    private UserViewModel createViewModel() {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);
    }

    private void bindLifeCycle() {
        mDataBinding.setLifecycleOwner(this);
    }

    private void bindContentView() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

}
