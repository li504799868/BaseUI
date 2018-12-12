package com.lzp.baseui.fragment.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by li.zhipeng on 2018/8/7
 * <p>
 * 可以同时显示多个Fragment的控制器
 */

public class MultiFragmentController extends FragmentController {

    public MultiFragmentController(FragmentManager fm, int containerViewId) {
        super(fm, containerViewId);
    }

    @Override
    public <T extends Fragment> T addFragment(Class<T> a) {
        T f = super.addFragment(a);
        showFragment(f);
        finishUpdate();
        return f;
    }

    @Override
    void showFragment(Fragment fragment) {
        super.showFragment(fragment);
        finishUpdate();
    }

    @Override
    void hideFragment(Fragment fragment) {
        super.hideFragment(fragment);
        finishUpdate();
    }
}
