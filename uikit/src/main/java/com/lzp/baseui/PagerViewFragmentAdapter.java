package com.lzp.baseui;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.lzp.baseui.fragment.BaseFragment;

/**
 * @author li.zhipeng on 2017/11/9.
 * <p>
 * 装载Framgent的adapter
 */

public class PagerViewFragmentAdapter extends FragmentStatePagerAdapter {

    private OnFragmentLoadListener listener;

    /**
     * 获取当前的Fragment
     */
    private BaseFragment currentFragment;

    public PagerViewFragmentAdapter(FragmentManager fm, OnFragmentLoadListener listener) {
        super(fm);
        this.listener = listener;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        currentFragment = (BaseFragment) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return listener.getFragmentPosition(position);
    }

    @Override
    public int getCount() {
        return listener.getPagerAdapterCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listener.getFragmentPositionTitle(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public interface OnFragmentLoadListener {

        /**
         * 加载指定位置的fragment
         *
         * @param position 加载的位置
         * @return fragment
         */
        Fragment getFragmentPosition(int position);

        /**
         * 加载指定位置的fragment的标题
         *
         * @param position 加载的位置
         * @return 标题
         */
        String getFragmentPositionTitle(int position);

        /**
         * fragment的数量
         *
         * @return 数量
         */
        int getPagerAdapterCount();

    }


}
