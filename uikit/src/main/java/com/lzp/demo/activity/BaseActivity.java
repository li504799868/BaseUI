package com.lzp.demo.activity;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;


/**
 * Activity基类
 *
 * @author li.zhipeng
 */
public abstract class BaseActivity extends FragmentActivity{

    /**
     * 上次点击返回的时间
     */
    private long lastBackTime = 0;

    /**
     * 是否需要返回提示
     */
    private boolean showBackTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFeature();
        // 加载View
        initView();
        // 加载数据
        loadData();

        this.getLifecycle().addObserver(new LifecycleObserver() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
    }

    /**
     * 设置页面的显示效果
     * */
    protected void setWindowFeature() {
    }

    /**
     * 初始化页面
     * */
    protected abstract void initView();

    /**
     * 加载网络数据
     */
    protected abstract void loadData();


    protected void removeAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        FragmentTransaction ft = fm.beginTransaction();
        for (android.support.v4.app.Fragment fragment : fragments) {
            if (fragment != null) {
                ft.remove(fragment);
            }
        }
        ft.commit();
        fm.executePendingTransactions();
    }

    /**
     * 是否要需要双击返回键有特殊处理
     * */
    public void setShowBackTip(boolean showBackTip) {
        this.showBackTip = showBackTip;
    }

    @Override
    public void onBackPressed() {
        if (showBackTip) {
            if (System.currentTimeMillis() - lastBackTime < 2000) {
                confirmBack();
            } else {
                // 执行确认返回的操作
                showBackTip();
                lastBackTime = System.currentTimeMillis();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 点击返回的提示
     */
    protected void showBackTip() {
    }

    /**
     * 确认返回的操作
     */
    protected void confirmBack() {
    }

    /**
     * 添加生命周期观察者
     * */
    public void addLifeCycleObserver(LifecycleObserver observer){
        getLifecycle().addObserver(observer);
    }

    /**
     * 重写此方法，子fragment才能出发回调onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments.size() > 0) {
            for (Fragment f : fragments) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
