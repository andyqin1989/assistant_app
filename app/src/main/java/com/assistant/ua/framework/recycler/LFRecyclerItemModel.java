package com.assistant.ua.framework.recycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by qinbaoyuan on 2018/09/08.
 * 列表Item数据的基类
 */
public class LFRecyclerItemModel<T> {
    public final static int TYPE_DEFAULT = 0x00;
    protected T data;
    public int viewType = TYPE_DEFAULT;
    protected Context context;
    private Class<? extends View> viewClass;


    public LFRecyclerItemModel(Context context, T data, Class<? extends View> viewClass) {
        this.context = context;
        this.viewClass = viewClass;
        this.data = data;

        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }

        if (viewClass == null) {
            throw new IllegalArgumentException("viewClass must not be null");
        }
    }

    public void setData(T data) {
        this.data = data;
    }

    RecyclerView.ViewHolder generateViewHolder() {
        LFViewHolder viewHolder = null;
        try {
            View view = viewClass.getConstructor(Context.class).newInstance(context);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            viewHolder = new LFViewHolder(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }


    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @SuppressWarnings("unchecked")
    public void onBind(final RecyclerView.ViewHolder holder, final int position) {
        if (holder.itemView instanceof IViewData) {
            ((IViewData) holder.itemView).update(data);
            if (itemOnClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClickListener.onItemClick(view, data, position);
                    }
                });
            }
        }
    }

    public interface ItemOnClickListener<T> {
        void onItemClick(View view, T data, int position);
    }
}
