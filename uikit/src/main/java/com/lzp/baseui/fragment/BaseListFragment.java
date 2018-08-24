package com.lzp.baseui.fragment;

import com.lzp.baseui.R;
import com.lzp.baseui.view.recyclerview.RefreshAndLoadRecyclerView;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p>
 * 封装的数据列表Fragment
 */
public abstract class BaseListFragment<T> extends BaseFragment
        implements RefreshAndLoadRecyclerView.OnRefreshAndLoadListener{

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

}
