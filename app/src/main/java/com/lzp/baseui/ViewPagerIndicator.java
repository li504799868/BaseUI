package com.lzp.baseui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by li.zhipeng on 2018/8/27.
 */
public class ViewPagerIndicator extends LinearLayout {

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindFragmentAdapter(final ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(adapter.getPageTitle(i));
            textView.setGravity(Gravity.CENTER);
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                }
            });
            addView(textView, params);
        }
    }
}
