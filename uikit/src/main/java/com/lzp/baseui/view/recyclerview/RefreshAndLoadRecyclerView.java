package com.lzp.baseui.view.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.lzp.baseui.manager.ListDataManager;
import com.lzp.baseui.recyclerview.BaseRecycleViewAdapter;
import com.lzp.baseui.view.CustomPtrClassicFrameLayout;

import java.util.List;

/**
 * @author li.zhipeng on 2017/10/19.
 * <p>
 * 可以下拉刷新和底部加载的RecyclerView
 */

public class RefreshAndLoadRecyclerView<T> extends CustomPtrClassicFrameLayout
        implements EndlessRecyclerOnScrollListener.OnListLoadNextPageListener,
        CustomPtrClassicFrameLayout.OnPullRefreshListener,
        ListDataManager.onDataChangedListener<T> {

    /**
     * 数据管理器
     */
    private ListDataManager<T> mListDataManager = new ListDataManager<>();
    private RecyclerView recyclerView;
    private BaseRecycleViewAdapter adapter;
    private OnRefreshAndLoadListener listener;

    /**
     * 底部加载更多的footer
     */
    private LoadMoreFooterState loadMoreFooterState;

    /**
     * 是否开启滑动到底部自动加载
     */
    private boolean scrollToLoad = true;

    public RefreshAndLoadRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshAndLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshAndLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        // 设置顶部刷新的监听
        setListener(this);
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(getEndlessRecyclerOnScrollListener());
        // 设置默认LoadMoreFooter
        loadMoreFooterState = new DefaultLoadMoreFooter(recyclerView);
        addView(recyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 绑定与ListDataManager数据的变化监听
        mListDataManager.addOnDataChangedListener(this);
    }


    private EndlessRecyclerOnScrollListener getEndlessRecyclerOnScrollListener() {
        return new EndlessRecyclerOnScrollListener(this);
    }

    /**
     * 设置某一页的数据
     */
    public void setPageData(List<T> list, String pageTag, boolean isLast) {
        mListDataManager.onGetListComplete(list, pageTag, isLast);
        // 如果是最后一页
        if (isLast) {
            if (loadMoreFooterState != null) {
                loadMoreFooterState.isLastState();
            }
        } else {
            if (loadMoreFooterState != null) {
                loadMoreFooterState.normalState();
            }
        }
    }

    /**
     * 设置某一页的数据
     */
    public void setPageData(List<T> list, boolean isLast) {
        mListDataManager.onGetListComplete(list, isLast);
        // 如果是最后一页
        if (isLast) {
            if (loadMoreFooterState != null) {
                loadMoreFooterState.isLastState();
            }
        } else {
            if (loadMoreFooterState != null) {
                loadMoreFooterState.normalState();
            }
        }
    }

    public BaseRecycleViewAdapter getAdapter() {
        return adapter;
    }

    public ListDataManager<T> getListDataManager() {
        return mListDataManager;
    }

    public void setListener(OnRefreshAndLoadListener listener) {
        this.listener = listener;
    }

    public void setScrollToLoad(boolean scrollToLoad) {
        this.scrollToLoad = scrollToLoad;
    }

    public void setAdapter(@NonNull BaseRecycleViewAdapter adapter) {
        this.adapter = adapter;
        // 添加新的footer
        if (this.loadMoreFooterState != null) {
            adapter.addFooterView(this.loadMoreFooterState.getFooterView(), adapter.getFooterViewsCount() - 1);
        }
        recyclerView.setAdapter(adapter);
    }

    public LoadMoreFooterState getLoadFooter() {
        return loadMoreFooterState;
    }

    /**
     * 滑动到底部加载更多的footer，只能添加一个
     */
    public void setLoadFooter(LoadMoreFooterState footerState) {
        // 如果footer已经添加过了，先移除之前的footer
        if (this.loadMoreFooterState != null && this.adapter != null) {
            adapter.removeFooterView(this.loadMoreFooterState.getFooterView());
        }
        this.loadMoreFooterState = footerState;
        // 添加新的footer
        if (this.loadMoreFooterState != null && this.adapter != null) {
            adapter.addFooterView(this.loadMoreFooterState.getFooterView(), adapter.getFooterViewsCount() - 1);
        }
    }

    @Override
    public void onLoadNextPage(RecyclerView view) {
        if (this.scrollToLoad) {
            if (loadMoreFooterState != null) {
                loadMoreFooterState.loadingState();
            }
            if (listener != null) {
                listener.requestPageIndex(mListDataManager.getPage(), mListDataManager.getPageTag());
            }
        }
    }

    @Override
    public void onPullRefresh() {
        if (listener != null) {
            listener.onPullRefresh(true);
        }
    }

    @Override
    public void onPullRefreshFailed() {
        if (listener != null) {
            listener.onPullRefresh(false);
        }
    }

    @Override
    public void onDataChanged(List<T> data) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnRefreshAndLoadListener {

        /**
         * 当无网络的时候 success为false
         */
        void onPullRefresh(boolean success);

        void requestPageIndex(int page, String pageTag);
    }
}
