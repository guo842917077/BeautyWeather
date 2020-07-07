package com.crazyorange.beauty.baselibrary.mvvm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class BaseCompatActivity<VM extends ViewModel, BD extends ViewDataBinding> extends AppCompatActivity {
    protected VM mViewModel;
    /**
     * 1.在 module 的 gradle 中开启 DataBinding 的支持会使用 APT 自动生成代码
     * 2.DataBinding 对象会持有所有布局中的控件
     */

    protected BD mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContentView();
        mViewModel = createViewModel();
        bindModelData();
        bindLifeCycle();
        registerListener();
    }

    protected abstract VM createViewModel();

    protected ViewModelProvider.AndroidViewModelFactory viewModelFactory() {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
    }

    protected void bindContentView() {
        mDataBinding = DataBindingUtil.setContentView(targetBindActivity(), layoutID());
    }

    protected abstract AppCompatActivity targetBindActivity();

    /**
     * Use DataBinding setXXX Method get ViewModel
     */
    protected abstract void bindModelData();

    protected abstract int layoutID();

    protected void bindLifeCycle() {
        mDataBinding.setLifecycleOwner(targetBindActivity());
    }


    protected abstract void registerListener();
}
