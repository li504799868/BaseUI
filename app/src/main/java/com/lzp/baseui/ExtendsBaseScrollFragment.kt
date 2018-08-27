package com.lzp.baseui

import android.os.Handler
import android.widget.Toast
import com.lzp.baseui.fragment.BaseScrollFragment

class ExtendsBaseScrollFragment : BaseScrollFragment() {

    private val handler = Handler()

    override fun getContentViewId(): Int = R.layout.fragment_extends_base_scroll

    override fun onPullRefreshFailed() {
        Toast.makeText(context, "onPullRefreshFailed", Toast.LENGTH_SHORT).show()
    }

    override fun onScrollBottom() {
        Toast.makeText(context, "onScrollBottom", Toast.LENGTH_SHORT).show()
    }

    override fun onPullRefresh() {
        handler.postDelayed({
            Toast.makeText(context, "onPullRefreshFailed", Toast.LENGTH_SHORT).show()
            refreshComplete()
        }, 2000)


    }

}
