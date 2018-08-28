package com.lzp.demo;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装BaseAdapter
 *
 * @author li.zhipeng
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected int id;

    private Context context;

    private List<T> list = new ArrayList<>();

    private int selectPosition = -1;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    /**
     * 构造函数
     */
    protected BaseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 构造函数
     */
    protected BaseAdapter(Context context, List<T> data) {
        this.context = context;
        setInitList(list);
    }

    /**
     * 重新设置内容
     */
    public void setInitList(List<T> list) {
        this.list.clear();
        setList(list);
    }

    /**
     * 设置list
     */
    public void setList(List<T> list) {
        this.list.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * 返回list
     */
    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected abstract int getLayoutId();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getLayoutId(), parent, false);
        }
        bindView(convertView, position);
        return convertView;
    }

    protected abstract void bindView(View convertView, int position);

    /**
     * 公共的ViewHolder
     * <p>
     * 在adapter中找到某个View，请使用公共的ViewHolder.getViewId(id)
     */
    public static class ViewHolder {

        /**
         * 获取adapter view id
         *
         * @param view adapter的convertView
         * @param id   指定View的id
         * @return convertView中指定id的View，已自动强转
         */
        @SuppressWarnings({"unchecked"})
        public static <T extends View> T getViewId(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            } else {
                viewHolder = (SparseArray<View>) view.getTag();

            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }


    }

}
