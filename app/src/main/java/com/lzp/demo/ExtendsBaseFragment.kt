package com.lzp.demo

import android.widget.Toast
import com.lzp.baseui.fragment.BaseFragment

/**
 * Created by li.zhipeng on 2018/8/27.
 */
class ExtendsBaseFragment : BaseFragment(){

    override fun getLayoutId(): Int = R.layout.fragment_extends_base

    override fun initView() {

    }

    override fun onVisible() {
        super.onVisible()
        Toast.makeText(context, "ExtendsBaseFragment onVisible", Toast.LENGTH_SHORT).show()
    }

}