package com.lzp.baseui.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.lzp.baseui.util.NetUtil;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandlerHook;

/**
 * 封装PtrClassicFrameLayout
 * <p>
 * author li.zhipeng
 */
public class CustomPtrClassicFrameLayout extends PtrClassicFrameLayout {

    private OnPullRefreshListener listener;

    /**
     * 刷新成功的延迟时间
     */
    private int refreshCompleteDelay = 1000;

    public CustomPtrClassicFrameLayout(Context context) {
        this(context, null);
    }

    public CustomPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context) {
        setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 如果有网络回调刷新接口
                if (NetUtil.isNetConnected(context)) {
                    // 开始刷新
                    if (listener != null) {
                        listener.onPullRefresh();
                    }
                } else {
                    // 2秒后刷新结束
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 回调刷新失败监听
                            if (listener != null) {
                                listener.onPullRefreshFailed();
                            }
                            refreshComplete();
                        }
                    }, refreshCompleteDelay);
                }
            }
        });
        // 设置刷新成功的延迟效果
        setRefreshCompleteHook(new PtrUIHandlerHook() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resume();
                    }
                }, refreshCompleteDelay);
            }
        });
        // 取消水平滑动
        disableWhenHorizontalMove(true);
    }

    /**
     * 设置刷新成功的延迟时间
     */
    public void setRefreshCompleteDelay(int refreshCompleteDelay) {
        this.refreshCompleteDelay = refreshCompleteDelay;
    }

    public void setListener(OnPullRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnPullRefreshListener {

        void onPullRefresh();

        /**
         * 刷新失败，会在无网络的时候，回调此方法
         */
        void onPullRefreshFailed();
    }
}
