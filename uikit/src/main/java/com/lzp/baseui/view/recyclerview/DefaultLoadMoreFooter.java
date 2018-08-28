package com.lzp.baseui.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzp.demo.R;

/**
 * Created by li.zhipeng on 2018/8/24.
 * <p>
 * 默认的LoadMoreFooter
 */
public class DefaultLoadMoreFooter implements LoadMoreFooterState {

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    private TextView textView;

    public DefaultLoadMoreFooter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public View getFooterView() {
        View contentView = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.footer_load_more_default, recyclerView, false);
        progressBar = contentView.findViewById(R.id.load_more_footer_loading);
        textView = contentView.findViewById(R.id.load_more_footer_text);
        return contentView;
    }

    @Override
    public void normalState() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
    }

    @Override
    public void loadingState() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
    }

    @Override
    public void isLastState() {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }
}
