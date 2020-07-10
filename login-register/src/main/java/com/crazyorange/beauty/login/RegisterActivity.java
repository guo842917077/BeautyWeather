package com.crazyorange.beauty.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.crazyorange.beauty.R;
import com.crazyorange.beauty.comm.config.RoutePage;
import com.crazyorange.beauty.databinding.ActivityRegisterBinding;
import com.crazyorange.beauty.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author guojinlong
 * <p>
 * 注册界面
 */
@Route(path = RoutePage.Login.REGISTER)
public class RegisterActivity extends AppCompatActivity {
    private UserViewModel mUserVM;
    private ActivityRegisterBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContentView();
        bindLifeCycle();
        addLine();
        mUserVM = createViewModel();
        bindViewModel();
        registerListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startOverAnimation();
    }

    private void addLine() {
        mDataBinding.tvLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mDataBinding.tvLogin.getPaint().setAntiAlias(true);
    }


    private void registerListener() {
        mDataBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mDataBinding.etUname.getText().toString();
                String password = mDataBinding.etPwd.getText().toString();
                String msg = "";
                if (mUserVM.isAccountOccupied(username)) {
                    msg = getResources().getString(R.string.account_is_occupied);
                    showSnackToastNotResp(msg);
                } else {
                    if (!isValidateAccount(username, password)) {
                        msg = getResources().getString(R.string.account_cannot_null);
                        showSnackToastNotResp(msg);
                        return;
                    }
                    // current account is not occupied , register a new user
                    boolean isSuccess = mUserVM.registerNewUser(username, password);
                    if (isSuccess) {
                        msg = getResources().getString(R.string.registered_success);
                        showSnackToastWithResp(msg);
                    } else {
                        msg = getResources().getString(R.string.registered_err);
                        showSnackToastNotResp(msg);
                    }
                }
            }
        });
        mDataBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePage.Login.LOGIN).navigation();
            }
        });
    }

    private boolean isValidateAccount(String username, String password) {
        if (username != null && !username.equals("") && password != null && !password.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public void showSnackToastNotResp(String message) {
        Snackbar.make(mDataBinding.constraintLayout2, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.red_login_err))
                .show();

    }

    public void showSnackToastWithResp(String msg) {
        Snackbar.make(mDataBinding.constraintLayout2, msg, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.login_register_btn_color))
                .setAction(R.string.confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                })
                .show();
    }


    public void startOverAnimation() {
        mDataBinding.imgOver.setSpeed(0.6f);
        mDataBinding.imgOver.playAnimation();
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
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }
}
