package com.lzp.baseui.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * 封装的Fragment的基类，带有懒加载功能
 *
 * @author li.zhipeng
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    private View rootView;

    public View getRootView() {
        return rootView;
    }

    protected boolean isVisible = false;
    /**
     * 默认是true
     */
    boolean isLazyLoad = true;

    private boolean hasPaused = true;

    /**
     * 设置layoutId
     */
    protected abstract int getLayoutId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract void initView();

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {

        if (rootView != null) {
            return (T) rootView.findViewById(id);
        }

        return null;
    }

    private boolean hasViewAdded() {
        return isAdded() && getView() != null
                && getView().getWindowToken() != null;
    }

    /**
     * 懒加载
     *
     * @param isVisibleToUser 是否对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!isVisible && rootView != null) {
                isVisible = true;
                onVisible();
            }

            if (isLazyLoad && rootView != null) {
                isLazyLoad = false;
                lazyLoad();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                if (hasViewAdded()) {
                    onInVisible();
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!isVisible) {
                isVisible = true;
                onVisible();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                if (hasViewAdded()) {
                    onInVisible();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (fragments.size() > 0) {
            for (Fragment f : fragments) {
                f.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hasPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        hasPaused = false;
    }

    /**
     * 没有显示给用户
     */
    protected void onInVisible() {

    }

    /**
     * 显示给用户
     */
    protected void onVisible() {

    }

    /**
     * 懒加载要做的工作，只有在页面第一次显示给用户的时候回调
     */
    protected void lazyLoad() {

    }

    protected boolean hasPaused() {
        return hasPaused;
    }

    /**
     * 设置懒加载
     */
    public void setLazyLoad(boolean lazyLoad) {
        this.isLazyLoad = lazyLoad;
    }

    /**
     * 添加生命周期观察者
     */
    public void addLifeCycleObserver(LifecycleObserver observer) {
        getLifecycle().addObserver(observer);
    }

}
