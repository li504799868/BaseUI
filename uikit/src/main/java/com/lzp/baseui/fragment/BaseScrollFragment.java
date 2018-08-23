package com.lzp.baseui.fragment;

import com.lzp.baseui.R;
import com.lzp.baseui.view.CustomPtrClassicFrameLayout;
import com.lzp.baseui.view.ScrollBottomLoadingView;

/**
 * 封装的可以顶部刷新，底部加载的ScrollView
 *
 * @author li.zhipeng 2018/08/23
 */
public abstract class BaseScrollFragment extends BaseFragment
        implements CustomPtrClassicFrameLayout.OnPullRefreshListener,
        ScrollBottomLoadingView.OnScrollToBottomListener {

    protected CustomPtrClassicFrameLayout customPtrClassicFrameLayout;

    private ScrollBottomLoadingView scrollView;

    public BaseScrollFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scroll;
    }

    @Override
    protected void initView() {
        customPtrClassicFrameLayout = findViewById(R.id.custom_ptr);
        customPtrClassicFrameLayout.setListener(this);

        scrollView = findViewById(R.id.scrollView);
        scrollView.setOnScrollToBottomListener(this);
        // 自定义CustomPtrClassicFrameLayout的属性
        initPtr();
        // 如果不是懒加载，创建就加载数据
        if (!isLazyLoad) {
            onRefresh();
        }
        // 如果对用户可见，加载数据
        else if (getUserVisibleHint()) {
            isLazyLoad = false;
            lazyLoad();
        }
    }

    /**
     * 自定义PtrClassicFrameLayout
     */
    private void initPtr() {
    }

    public void refreshComplete() {
        if (customPtrClassicFrameLayout != null) {
            customPtrClassicFrameLayout.refreshComplete();
        }
    }

    /**
     * 设置可以顶部刷新
     */
    public void setPullToRefresh(boolean canRefresh) {
        customPtrClassicFrameLayout.setPullToRefresh(canRefresh);
    }

    /**
     * 设置可以底部刷新
     */
    public void setScrollBottomToLoad(boolean canLoad) {
        scrollView.setCanLoad(canLoad);
    }

    @Override
    public void onPullRefresh() {
        onRefresh();
    }

    protected void onRefresh() {

    }

    @Override
    public void onScrollBottom() {

    }

}
