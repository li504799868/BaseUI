package com.lzp.demo

import android.content.Context
import com.lzp.demo.fragment.BaseListFragment
import com.lzp.demo.recyclerview.BaseRecycleViewAdapter
import com.lzp.demo.recyclerview.RecyclerViewHolder


class ExtendsBaseListFragment : BaseListFragment<String>() {

    override fun initView() {
        super.initView()
        setAdapter(MyAdapter(requireContext()))
    }

    override fun lazyLoad() {
        super.lazyLoad()
        addData()
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

        override fun getLayoutId(viewType: Int): Int = R.layout.item_text

        override fun convert(holder: RecyclerViewHolder, item: String?, position: Int) {
            holder.setText(R.id.textView, item)
        }

    }

}
