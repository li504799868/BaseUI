package com.lzp.baseui.fragment;

import android.support.v7.widget.RecyclerView;

import com.lzp.baseui.R;
import com.lzp.baseui.recyclerview.BaseRecycleViewAdapter;
import com.lzp.baseui.view.recyclerview.RefreshAndLoadRecyclerView;

import java.util.List;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p>
 * 封装的数据列表Fragment
 */
public abstract class BaseListFragment<T> extends BaseFragment
        implements RefreshAndLoadRecyclerView.OnRefreshAndLoadListener {

    protected RefreshAndLoadRecyclerView<T> refreshAndLoadRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        refreshAndLoadRecyclerView = findViewById(R.id.refresh_load_recyclerview);
        refreshAndLoadRecyclerView.setListener(this);
    }

    public void refreshComplete(){
        refreshAndLoadRecyclerView.refreshComplete();
    }

    public void setAdapter(BaseRecycleViewAdapter<T> adapter){
        refreshAndLoadRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置某一页的数据
     */
    public void setPageData(List<T> list, String pageTag, boolean isLast) {
        refreshAndLoadRecyclerView.setPageData(list, pageTag, isLast);
    }

    /**
     * 设置某一页的数据
     */
    public void setPageData(List<T> list, boolean isLast) {
        refreshAndLoadRecyclerView.setPageData(list, isLast);
    }

}
