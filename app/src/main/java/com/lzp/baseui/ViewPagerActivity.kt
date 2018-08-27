package com.lzp.baseui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.lzp.baseui.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_base_fragment.*

class ViewPagerActivity : AppCompatActivity(), PagerViewFragmentAdapter.OnFragmentLoadListener<BaseFragment> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_fragment)

        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = PagerViewFragmentAdapter<BaseFragment>(supportFragmentManager, this)
    }

    override fun getFragmentPosition(position: Int): BaseFragment {
        return when (position) {
            0 -> ExtendsBaseFragment()
            1 -> ExtendsBaseScrollFragment()
            else -> ExtendsBaseListFragment()
        }
    }

    override fun getFragmentPositionTitle(position: Int): String = ""

    override fun getPagerAdapterCount(): Int = 3
}
