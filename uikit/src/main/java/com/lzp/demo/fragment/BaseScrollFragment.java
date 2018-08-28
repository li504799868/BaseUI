package com.lzp.demo.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzp.demo.R;
import com.lzp.demo.view.CustomPtrClassicFrameLayout;
import com.lzp.demo.view.RefreshAndLoadScrollView;
import com.lzp.demo.view.ScrollBottomLoadingView;

/**
 * 封装的可以顶部刷新，底部加载的ScrollView
 *
 * @author li.zhipeng 2018/08/23
 */
public abstract class BaseScrollFragment extends BaseFragment
        implements CustomPtrClassicFrameLayout.OnPullRefreshListener,
        ScrollBottomLoadingView.OnScrollToBottomListener {

    protected RefreshAndLoadScrollView refreshAndLoadScrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scroll;
    }

    @Override
    protected void initView() {
        refreshAndLoadScrollView = findViewById(R.id.refresh_load_scrollview);
        refreshAndLoadScrollView.setListener(this);
        refreshAndLoadScrollView.setOnScrollToBottomListener(this);

        // 添加内容的View
        LayoutInflater.from(getContext()).inflate(getContentViewId(), (ViewGroup) refreshAndLoadScrollView.getContentView());
    }

    protected abstract int getContentViewId();

    public void refreshComplete() {
        refreshAndLoadScrollView.refreshComplete();
    }

}
