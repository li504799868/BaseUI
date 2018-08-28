package com.lzp.demo.view.recyclerview;

import android.view.View;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p>
 * RecyclerView滑动到底部自动刷新的Footer需要实现的接口
 * <p>
 * 会返回三种状态：
 * normal: 普通状态
 * loading：加载状态
 * isLast：已经是最后一页
 */
public interface LoadMoreFooterState {

    /**
     * 返回指定的footer的内容View
     */
    View getFooterView();

    /**
     * Normal状态
     */
    void normalState();

    /**
     * 加载状态
     */
    void loadingState();

    /**
     * 已经是最后一页状态
     */
    void isLastState();

}
