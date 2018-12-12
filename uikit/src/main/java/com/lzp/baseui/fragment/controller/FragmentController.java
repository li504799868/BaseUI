package com.lzp.baseui.fragment.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lzp.baseui.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhipeng 2017.9.20
 */

public class FragmentController {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

    private int mContainerViewId = 0;

    /**
     * 保存目前的Fragments
     */
    List<FragmentInfo> mFragments;
    private boolean attackToActivityAfterCreate;
    private boolean restartInError = false;

    FragmentController(FragmentManager fm) {
        this(fm, 0);
    }

    FragmentController(FragmentManager fm, int containerViewId) {
        mFragmentManager = fm;
        mContainerViewId = containerViewId;
        mFragments = new ArrayList<>();
    }

    /**
     * 添加指定类型的Fragment，并指定唯一tag
     */
    public <T extends Fragment> T addFragment(Class<T> a, String tag) {
        int position = mFragments.size();
        FragmentInfo info = new FragmentInfo();
        T fragment = (T) getFragmentFromActivity(tag);
        if (fragment == null) {
            fragment = ClassUtils.createClass(a);

            info.fragment = fragment;
            mFragments.add(info);

            if (!attackToActivityAfterCreate) {
                attackToActivity(position);
            }
        } else {
            restartInError = true;
            info.fragment = fragment;
            mFragments.add(info);
            info.hasAdded = true;
            createTransaction();
            if (fragment.isDetached()) {
                mCurTransaction.attach(fragment);
            }
            hideFragment(fragment);
        }
        return fragment;
    }

    /**
     * 默认情况下，使用Fragment的ClassName作为唯一tag，如果添加了相同类型的Fragment，请使用
     * <p>
     * {@link FragmentController#addFragment(Class, String)}, 指定唯一tag
     */
    public <T extends Fragment> T addFragment(Class<T> a) {
        return addFragment(a, getFragmentTag(a));
    }

    void onStart() {
    }

    boolean isRestartError() {
        return restartInError;
    }

    public void onRestoreInstance(Bundle savedInstanceState) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onDestroy() {
        mFragments.clear();
    }

    private void attackToActivity(int position) {
        FragmentInfo info = mFragments.get(position);
        if (info.hasAdded) {
            return;
        }
        createTransaction();

        // Do we already have this fragment?
        Fragment fragment = info.fragment;
        String name = getFragmentTag(fragment.getClass());
        if (mContainerViewId != 0) {
            mCurTransaction.add(mContainerViewId, fragment, name);
        } else {
            mCurTransaction.add(fragment, name);
        }
        info.hasAdded = true;

        hideFragment(fragment);
    }

    public void start(int position) {
        innerStart(position);
    }

    private void innerStart(int position) {
        FragmentInfo info = mFragments.get(position);
        attackToActivity(position);
        showFragment(info.fragment);
        finishUpdate();
    }

    void stop(int position) {
        innerStop(position);
    }

    private void innerStop(int position) {
        FragmentInfo info = mFragments.get(position);
        attackToActivity(position);
        hideFragment(info.fragment);
        finishUpdate();
    }

    public void stop() {
        for (int i = 0; i < mFragments.size(); i++) {
            stop(i);
        }
    }

    void showFragment(Fragment fragment) {
        createTransaction();
        mCurTransaction.show(fragment);
    }

    void hideFragment(Fragment fragment) {
        createTransaction();
        mCurTransaction.hide(fragment);
    }

    private void createTransaction() {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
    }

    public void setAttackToActivityAfterCreate() {
        this.attackToActivityAfterCreate = true;
    }

    private void destroyItem(int position) {
        createTransaction();
        FragmentInfo info = mFragments.get(position);
        mCurTransaction.detach(info.fragment);
    }

    void finishUpdate() {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    public Fragment getFragmentFromActivity(Class clazz) {
        return getFragmentFromActivity(getFragmentTag(clazz));
    }

    private Fragment getFragmentFromActivity(String name) {
        return mFragmentManager.findFragmentByTag(name);
    }

    /**
     * 获取每一个Fragment的唯一tag
     */
    private String getFragmentTag(Class clazz) {
        return clazz.getCanonicalName();
    }

    public static void show(FragmentManager manager, Fragment f) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(f, f.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    public int getShowing() {
        return 0;
    }

    class FragmentInfo {
        Fragment fragment;
        boolean hasAdded;
    }
}
