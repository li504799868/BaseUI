package com.lzp.baseui.fragment.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by apple on 17/3/31.
 * <p>
 * 同时只有一个Fragment显示的Controller
 */

public class SingleFragmentController extends FragmentController {

    public static final String FRAGMENTATE_STATE_SAVE_CURRENT = "fragmentation_state_save_status";

    private int mCurrentPrimaryItemPosition = 0;
    private boolean needRefreshShowState = false;

    public SingleFragmentController(FragmentManager fm) {
        super(fm);
    }

    public SingleFragmentController(FragmentManager fm, int primerPosition) {
        super(fm);
        mCurrentPrimaryItemPosition = primerPosition;
    }

    public SingleFragmentController(FragmentManager fm, int primerPosition, int containerViewId) {
        super(fm, containerViewId);
        mCurrentPrimaryItemPosition = primerPosition;
    }

    @Override
    public void onRestoreInstance(Bundle savedInstanceState) {
        super.onRestoreInstance(savedInstanceState);
        if (savedInstanceState != null) {
            // 隐藏所有的Fragment
            for (FragmentInfo info : mFragments) {
                hideFragment(info.fragment);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 记录当前选中Fragment的位置
        outState.putInt(FRAGMENTATE_STATE_SAVE_CURRENT,
                mCurrentPrimaryItemPosition);
    }

    @Override
    public <T extends Fragment> T addFragment(Class<T> a) {
        T f = super.addFragment(a);
        int position = mFragments.size() - 1;
        if (position == mCurrentPrimaryItemPosition) {
            super.start(position);
        }
        needRefreshShowState = isRestartError();
        return f;
    }

    @Override
    public void start(int position) {
        if (position != mCurrentPrimaryItemPosition) {
            super.stop(mCurrentPrimaryItemPosition);
            super.start(position);
            mCurrentPrimaryItemPosition = position;
        }
    }

    @Override
    public int getShowing() {
        return mCurrentPrimaryItemPosition;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (needRefreshShowState) {
            needRefreshShowState = false;
            for (int i = 0; i < mFragments.size(); i++) {
                FragmentInfo info = mFragments.get(i);
                if (info.hasAdded) {
                    if (i == mCurrentPrimaryItemPosition) {
                        showFragment(info.fragment);
                    } else {
                        hideFragment(info.fragment);
                    }
                }
            }
            finishUpdate();
        }
    }
}
