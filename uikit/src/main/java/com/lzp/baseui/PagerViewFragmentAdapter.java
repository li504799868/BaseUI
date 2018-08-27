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

public class PagerViewFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private OnFragmentLoadListener<T> listener;

    /**
     * 获取当前的Fragment
     */
    private T currentFragment;

    public PagerViewFragmentAdapter(FragmentManager fm, OnFragmentLoadListener<T> listener) {
        super(fm);
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        currentFragment = (T) object;
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

    @SuppressWarnings("unchecked")
    public T getCurrentFragment() {
        return (T) currentFragment;
    }

    public interface OnFragmentLoadListener<T> {

        /**
         * 加载指定位置的fragment
         *
         * @param position 加载的位置
         * @return fragment
         */
        T getFragmentPosition(int position);

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
