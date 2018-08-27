package com.lzp.baseui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.lzp.baseui.fragment.BaseFragment

class ViewPagerActivity : AppCompatActivity(), PagerViewFragmentAdapter.OnFragmentLoadListener<BaseFragment> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_fragment)

        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = PagerViewFragmentAdapter<BaseFragment>(supportFragmentManager, this)

        findViewById<ViewPagerIndicator>(R.id.viewpager_indicator).bindFragmentAdapter(viewpager)
    }

    override fun getFragmentPosition(position: Int): BaseFragment {
        return when (position) {
            0 -> ExtendsBaseFragment()
            1 -> ExtendsBaseScrollFragment()
            else -> ExtendsBaseListFragment()
        }
    }

    override fun getFragmentPositionTitle(position: Int): String {
        return when (position) {
            0 -> "Fragment"
            1 -> "ScrollFragment"
            else -> "ListFragment"
        }
    }

    override fun getPagerAdapterCount(): Int = 3
}
