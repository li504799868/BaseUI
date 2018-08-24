package com.lzp.baseui.manager;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页加载管理器
 *
 * @author li.zhipeng
 */

public class ListDataManager<T> {

    /**
     * 列表数据
     */
    private List<T> data = new ArrayList<>();

    /**
     * 是否已经是最后一页
     */
    private boolean mIsLastPage;

    /**
     * 当前的页数
     */
    private int page = 1;

    /**
     * 设置当前分页标记，可用于String类型分页
     */
    private String pageTag;

    /**
     * 当数据发生变化的回调监听
     */
    private List<onDataChangedListener<T>> onDataChangedListeners = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    int getCount() {
        return data.size();
    }

    public void setLastPage(boolean isLast) {
        this.mIsLastPage = isLast;
    }

    public boolean isLastPage() {
        return mIsLastPage;
    }

    /**
     * 指定要刷新的页数，一般只用来设置初始值
     */
    public void setLoadPage(int page) {
        this.page = page;
    }

    /**
     * 刷新所有的数据
     *
     * @param pageTag 要刷新的pageTag
     */
    public void setLoadPageTag(String pageTag) {
        this.pageTag = pageTag;
    }

    public boolean hasData() {
        return data != null && !data.isEmpty();
    }

    public int getPage() {
        return page;
    }

    public void setPageTag(String pageTag) {
        this.pageTag = pageTag;
    }

    public String getPageTag() {
        return pageTag;
    }

    /**
     * 添加数据到列表中
     */
    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyDataChangedListener();
    }

    /**
     * 从列表中移除数据
     */
    public void removeData(List<T> data) {
        this.data.removeAll(data);
        notifyDataChangedListener();
    }

    /**
     * 重置页数信息
     */
    private void reset() {
        data.clear();
        page = 1;
        pageTag = null;
        this.mIsLastPage = true;
    }

    /**
     * 把加载到的数据添加到列表中
     */
    public void onGetListComplete(List<T> data, boolean isLastPage) {
        onGetListComplete(data, null, isLastPage);
    }

    /**
     * 把加载到的数据添加到列表中
     */
    public void onGetListComplete(List<T> data, String pageTag, boolean isLastPage) {
        // 如果是第一页
        if (page == 1) {
            data.clear();
        }
        // page自增长+1
        page++;
        // 指定当前页的标记
        this.pageTag = pageTag;

        // 把数据添加到列表中
        if (data != null && data.size() > 0) {
            addData(data);
        }

        // 是否已经是最后一页
        this.mIsLastPage = isLastPage;
    }

    public void addOnDataChangedListener(ListDataManager.onDataChangedListener<T> onDataChangedListener) {
        this.onDataChangedListeners.add(onDataChangedListener);
    }

    public void removeOnDataChangedListener(ListDataManager.onDataChangedListener<T> onDataChangedListener) {
        this.onDataChangedListeners.remove(onDataChangedListener);
    }

    /**
     * 回调数据变化的监听函数
     */
    public void notifyDataChangedListener() {
        if (onDataChangedListeners.size() > 0) {
            for (onDataChangedListener<T> listener : onDataChangedListeners) {
                listener.onDataChanged(data);
            }
        }
    }

    /**
     * 当数据发生变化的回调监听
     */
    public interface onDataChangedListener<T> {
        void onDataChanged(List<T> data);
    }


}
