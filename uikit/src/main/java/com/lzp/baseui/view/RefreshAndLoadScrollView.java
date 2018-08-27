package com.lzp.baseui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p>
 * 可以顶部刷新和底部加载的ScrollView
 */
public class RefreshAndLoadScrollView extends CustomPtrClassicFrameLayout {

    private ScrollBottomLoadingView scrollBottomLoadingView;

    public RefreshAndLoadScrollView(Context context) {
        this(context, null);
    }

    public RefreshAndLoadScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshAndLoadScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 添加滑动到底部load的ScrollView
        scrollBottomLoadingView = new ScrollBottomLoadingView(context);
        scrollBottomLoadingView.setCanLoad(true);
        addView(scrollBottomLoadingView,
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public void addView(View child) {
        scrollBottomLoadingView.addView(child);
    }

    public void addView(View child, int position) {
        scrollBottomLoadingView.addView(child, position);
    }

    public View getContentView() {
        return scrollBottomLoadingView.getChildAt(0);
    }

    public void setCanLoad(Boolean canLoad) {
        scrollBottomLoadingView.setCanLoad(canLoad);
    }

    public void setOnScrollToBottomListener(ScrollBottomLoadingView.OnScrollToBottomListener listener) {
        scrollBottomLoadingView.setOnScrollToBottomListener(listener);
    }
}
