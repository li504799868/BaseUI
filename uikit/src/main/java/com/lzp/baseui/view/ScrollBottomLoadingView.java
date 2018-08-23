package com.lzp.baseui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 可以滑动到底部刷新的ScrollView
 *
 * @author li.zhipeng
 */
public class ScrollBottomLoadingView extends ScrollView {

    private int downY;
    private int mTouchSlop;
    private OnScrollToBottomListener onScrollToBottom;

    /**
     * 是否要监听滑动到底部的事件
     */
    private boolean canLoad;

    public ScrollBottomLoadingView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollBottomLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public ScrollBottomLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
            default:
                return super.onInterceptTouchEvent(e);
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        // 是否允许监听
        if (!canLoad) {
            return;
        }

        if (scrollY != 0 && null != onScrollToBottom) {
            // 如果是false，需要额外判断，解决部分手机的兼容问题，例如锤子
            if (!clampedY) {
                // 解决在锤子
                if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                    onScrollToBottom.onScrollBottom();
                }
            } else {
                onScrollToBottom.onScrollBottom();
            }
        }
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }

    public void setOnScrollToBottomListener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener {

        /**
         * 滚动到底部
         */
        void onScrollBottom();
    }
}