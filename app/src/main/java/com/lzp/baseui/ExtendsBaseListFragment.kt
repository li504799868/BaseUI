package com.lzp.baseui

import android.content.Context
import com.lzp.baseui.fragment.BaseListFragment
import com.lzp.baseui.recyclerview.BaseRecycleViewAdapter
import com.lzp.baseui.recyclerview.RecyclerViewHolder


class ExtendsBaseListFragment : BaseListFragment<String>() {

    override fun initView() {
        super.initView()
        setAdapter(MyAdapter(requireContext()))
    }

    override fun onPullRefresh(success: Boolean) {
        addData()
        refreshComplete()
    }

    override fun requestPageIndex(page: Int, pageTag: String?) {
        addData()
    }

    private fun addData() {
        setPageData(arrayListOf("111", "111", "111", "111", "111",
                "111", "111", "111", "111", "111"),
                "test", false)
    }

    private inner class MyAdapter(context: Context)
        : BaseRecycleViewAdapter<String>(context) {

        override fun getLayoutId(viewType: Int): Int = android.R.layout.simple_list_item_1

        override fun convert(holder: RecyclerViewHolder, item: String?, position: Int) {
            holder.setText(android.R.id.text1, item)
        }

    }

}
