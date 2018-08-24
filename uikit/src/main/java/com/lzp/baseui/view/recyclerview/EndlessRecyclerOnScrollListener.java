package com.lzp.baseui.view.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p/>
 * 继承自RecyclerView.OnScrollListener，可以监听到是否滑动到页面最低部
 */
public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * layoutManager
     */
    private RecyclerView.LayoutManager layoutManager;

    /**
     * 当前RecyclerView类型
     */
    private LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 滑动底部的数量的差值
     * <p>
     * 注意：需要加上footer的数量
     */
    private int loadNextOffsetCount = 0;

    /**
     * 滑动到底部的监听
     */
    private OnListLoadNextPageListener listener;

    /**
     * 设置底部刷新的差值
     */
    public void setLoadNextOffsetCount(int loadNextOffsetCount) {
        this.loadNextOffsetCount = loadNextOffsetCount;
    }

    public void setListener(OnListLoadNextPageListener listener) {
        this.listener = listener;
    }

    public EndlessRecyclerOnScrollListener(OnListLoadNextPageListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 如果监听是空的，直接返回
        if (listener == null) {
            return;
        }

        if (layoutManager == null) {
            layoutManager = recyclerView.getLayoutManager();
        }
        // 如果没有设置layoutManager，直接返回
        if (layoutManager == null) {
            return;
        }

        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        // 只有在滚动停止状态才加载下一页
        if ((visibleItemCount > 0 && (lastVisibleItemPosition) >= totalItemCount - loadNextOffsetCount)) {
            listener.onLoadNextPage(recyclerView);
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    /**
     * 取数组中最大值
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public interface OnListLoadNextPageListener {
        void onLoadNextPage(RecyclerView view);
    }
}
