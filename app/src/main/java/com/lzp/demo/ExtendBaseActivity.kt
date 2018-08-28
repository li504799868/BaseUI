package com.lzp.demo

import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.lzp.demo.activity.BaseActivity

class ExtendBaseActivity : BaseActivity() {

    private val lifeCycleBean = LifeCycleBean(this)

    override fun setWindowFeature() {
        super.setWindowFeature()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setShowBackTip(true)
    }

    override fun initView() {
        setContentView(R.layout.activity_extend_base)
    }

    override fun loadData() {
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "BaseActivity"
        // 为LifeCycleBean绑定Activity的周期
        addLifeCycleObserver(lifeCycleBean)
    }

    override fun showBackTip() {
        super.showBackTip()
        Toast.makeText(this, "再按一次退出页面", Toast.LENGTH_SHORT).show()
    }

    override fun confirmBack() {
        super.confirmBack()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifeCycleBean)
    }

}
