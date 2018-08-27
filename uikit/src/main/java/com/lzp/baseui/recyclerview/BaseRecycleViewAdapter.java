package com.lzp.baseui.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li.zhipeng on 2018/08/23.
 * <p>
 * 封装的RecyclerView.Adapter的基类
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final int TYPE_VIEW = 100000;
    /**
     * header集合
     */
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    /**
     * footer集合
     */
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();

    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();

    private Context mContext;

    private List<T> mDatas;

    private OnItemClickListener<T> onItemClickListener;

    private OnItemLongClickListener<T> onItemLongClickListener;

    /**
     * 构造器
     */
    protected BaseRecycleViewAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 构造器
     */
    protected BaseRecycleViewAdapter(Context context, List<T> mDatas) {
        this(context);
        this.mDatas = mDatas;
    }

    /**
     * 返回指定的item的layoutId
     */
    protected abstract int getLayoutId(int viewType);


    /**
     * 添加header
     */
    public void addHeaderView(View headerView) {
        if (mHeaderViews.contains(headerView)) {
            return;
        }
        mHeaderViews.add(headerView);
    }

    /**
     * 指定位置添加header
     */
    public void addHeaderView(View headerView, int position) {
        if (mHeaderViews.contains(headerView)) {
            return;
        }
        mHeaderViews.add(position, headerView);
    }

    /**
     * 移除所有headers
     */
    public void removeAllHeaders() {
        mHeaderViews.clear();
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    public void addFooterView(View footerView, int position) {
        mFooterViews.add(position, footerView);
    }

    public void removeHeaderView(View headerView) {
        mHeaderViews.remove(headerView);
    }

    public void removeFooterView(View headerView) {
        mFooterViews.remove(headerView);
    }

    /**
     * Grid  header footer占据位图
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == position * TYPE_VIEW
                            ? getHeaderAndFooterSpan(gridManager) : 1;
                }
            });
        }
    }

    /**
     * 获取GridLayoutManager的列数
     */
    private int getHeaderAndFooterSpan(GridLayoutManager gridManager) {
        return gridManager.getSpanCount();
    }

    /**
     * StaggeredGrid  header footer占据位图
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (holder.getLayoutPosition() < mHeaderViews.size() || holder.getLayoutPosition() >= getItemCount() - mFooterViews.size())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            //占据全部空间
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            //用position作为HeaderView的ViewType标记
            //记录每个ViewType标记
            mHeaderViewTypes.add(position * TYPE_VIEW);
            return position * TYPE_VIEW;
        }

        if (mFooterViews.size() > 0 && position > getListCount() - 1 + mHeaderViews.size()) {
            //用position作为FooterView的ViewType标记
            //记录每个ViewType标记
            mFooterViewTypes.add(position * TYPE_VIEW);
            return position * TYPE_VIEW;
        }

        if (mHeaderViews.size() > 0) {
            return getListViewType(position - mHeaderViews.size());
        }

        return getListViewType(position);
    }

    /**
     * Item页布局类型个数，默认为1
     */
    protected int getListViewType(int position) {
        return 1;
    }

    public int getListCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 返回header
        if (mHeaderViewTypes.contains(viewType)) {
            return new HeaderHolder(mHeaderViews.get(viewType / TYPE_VIEW));
        }
        // 返回footer
        if (mFooterViewTypes.contains(viewType)) {
            int index = viewType / TYPE_VIEW - getListCount() - mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(index));
        }
        return onCreateBaseViewHolder(parent, viewType);
    }

    /**
     * 创建ViewHolder
     *
     * @param parent   RecycleView对象
     * @param viewType item的类型
     * @return Holder对象
     */
    private RecyclerViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent,
                false);
        // 此处可以自定义ViewHolder，优先使用自定义的holder，请重写createViewHolder
        RecyclerViewHolder customHolder = createViewHolder(itemView, parent, viewType);
        if (customHolder != null) {
            return customHolder;
        } else {
            // 使用通过的ViewHolder
            return RecyclerViewHolder.get(mContext, itemView, parent, viewType);
        }
    }

    /**
     * 如果希望有新的viewholder
     *
     * @param itemView
     * @param parent
     * @param viewType
     * @return
     */
    private RecyclerViewHolder createViewHolder(View itemView, ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // 判断是否是footer，不处理
        if (mFooterViews.size() > 0 && (position > getListCount() - 1 + mHeaderViews.size())) {
            return;
        }
        // 判断是否是header，不处理
        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
        }
        onBindBaseViewHolder(holder, position - mHeaderViews.size());
    }

    private void onBindBaseViewHolder(final RecyclerViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, mDatas.get(position), position);
                }
            });
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClickListener.onItemLongClick(holder.itemView, mDatas.get(position), position);
                }
            });
        }

        convert(holder, mDatas.get(position), position);
    }

    /**
     * 设置每个页面显示的内容
     *
     * @param holder itemHolder
     * @param item   每一Item显示的数据ø
     */
    protected abstract void convert(RecyclerViewHolder holder, T item, int position);

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getListCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getListCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getListCount() + mFooterViews.size();
        }

        return getListCount();
    }

    /**
     * 获取所有数据
     */
    public List<T> getLists() {
        return mDatas;
    }

    /**
     * 根据位置插入
     *
     * @param position
     * @param t
     */
    public void addData(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * 插入一组list
     */
    public void setList(List<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 插入一组list
     */
    public void setData(List<T> list) {
        mDatas = list;
    }

    /**
     * 单独移除某一个
     */
    public void removeData(int position) {
        if (!mDatas.isEmpty()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }

    /**
     * 全部移除
     */
    public void removeAllData() {
        if (!mDatas.isEmpty()) {
            mDatas.clear();
            this.notifyDataSetChanged();
        }
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 判断指定位置是否是header
     */
    public boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < mHeaderViews.size();
    }

    /**
     * 判断指定位置是否是footer
     */
    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - mFooterViews.size();
        return getFooterViewsCount() > 0 && position >= lastPosition;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, T t, int position);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View itemView, T t, int position);
    }

    /**
     * header的ViewHolder
     */
    private class HeaderHolder extends RecyclerViewHolder {

        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * footer的ViewHolder
     */
    private class FooterHolder extends RecyclerViewHolder {

        FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
