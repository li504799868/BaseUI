package com.lzp.baseui.recyclerview;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * RecyclerView的item间距
 * <p>
 * author li.zhipeng
 */
public class RecyclerViewSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int horizontalSpace, verticalSpace;

    public RecyclerViewSpacesItemDecoration(int space) {
        this.horizontalSpace = verticalSpace = space;
    }

    public RecyclerViewSpacesItemDecoration(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            // view当前的位置
            int position = parent.getChildAdapterPosition(view) + 1;
            // 总数
            int total = state.getItemCount();
            // 判断是不是每行的最后一个
            int spanCount = gridLayoutManager.getSpanCount();
            // 计算每一个item的间距
            int spaceL = horizontalSpace / (spanCount - 1);
            // 首先判断是不是最后一行
            if (gridLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                // 如果是第一个
                if (position % spanCount == 1) {
                    outRect.bottom = verticalSpace;
                }
                // 如果是最后一个
                else if (position % spanCount == 0) {
                    outRect.top = verticalSpace;
                }
                // 其他
                else {
                    outRect.bottom = verticalSpace;
                    outRect.top = verticalSpace;
                }
                // 判断是否是最后一行
                if (position <= total - spanCount) {
                    outRect.right = horizontalSpace;
                }
            } else {
                // 如果是第一个
                if (position % spanCount == 1) {
                    outRect.right = spaceL;
                }
                // 如果是最后一个
                else if (position % spanCount == 0) {
                    outRect.left = spaceL;
                }
                // 其他
                else {
                    outRect.right = spaceL;
                    outRect.left = spaceL;
                }
                // 判断是否是最后一行
                if (position <= total - spanCount) {
                    outRect.bottom = verticalSpace;
                }
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            // 判断如果不是最后一个
            // view当前的位置
            int position = parent.getChildAdapterPosition(view) + 1;
            if (position != state.getItemCount()) {
                // 判断方向
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    outRect.right = horizontalSpace;
                } else {
                    outRect.bottom = verticalSpace;
                }
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            // view当前的位置
            int position = parent.getChildAdapterPosition(view) + 1;
            // 总数
            int total = state.getItemCount();
            // 判断是不是每行的最后一个
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            // 计算每一个item的间距
            int spaceL = horizontalSpace / (spanCount - 1);

            // 首先判断是不是最后一行
            if (staggeredGridLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                // 如果是第一个
                if (position % spanCount == 1) {
                    outRect.bottom = spaceL;
                }
                // 如果是最后一个
                else if (position % spanCount == 0) {
                    outRect.top = spaceL;
                }
                // 其他
                else {
                    outRect.bottom = spaceL;
                    outRect.top = spaceL;
                }
                // 判断是否是最后一行
                if (position <= total - spanCount) {
                    outRect.right = horizontalSpace;
                }
            } else {
                // 如果是第一个
                if (position % spanCount == 1) {
                    outRect.right = spaceL;
                }
                // 如果是最后一个
                else if (position % spanCount == 0) {
                    outRect.left = spaceL;
                }
                // 其他
                else {
                    outRect.right = spaceL;
                    outRect.left = spaceL;
                }
                // 判断是否是最后一行
                if (position <= total - spanCount) {
                    outRect.bottom = verticalSpace;
                }
            }
        }
    }
}
