package com.assistant.ua.framework.recycler;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbaoyuan on 2018/09/08.
 * RecyclerView适配器基类
 */
public class LFRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LFRecyclerItemModel> viewItemModels = new ArrayList<>();

    public void addItem(LFRecyclerItemModel model) {
        viewItemModels.add(model);
    }

    public LFRecyclerItemModel getItemModel(int viewType) {
        LFRecyclerItemModel itemModel = null;
        for (LFRecyclerItemModel model : viewItemModels) {
            if (model.viewType == viewType) {
                itemModel = model;
                break;
            }
        }
        return itemModel;
    }

    public void addItemPosition(LFRecyclerItemModel model, int position) {
        viewItemModels.add(position, model);
    }

    public void resetAdapter() {
        viewItemModels.clear();
    }

    public void removeItem(int viewType) {
        for (LFRecyclerItemModel model : viewItemModels) {
            if (model.viewType == viewType) {
                viewItemModels.remove(model);
                break;
            }
        }
    }

    public void removeItemRange(int start, int end) {
        while (end >= start) {
            if (viewItemModels.size() > 0 && viewItemModels.size() > end) {
                viewItemModels.remove(end);
            }
            end--;
        }
    }

    public int getItemPosition(int viewType) {
        for (int i = 0; i < viewItemModels.size(); i++) {
            if (viewItemModels.get(i).viewType == viewType) {
                return i;
            }
        }
        return -1;
    }

    private RecyclerView.ViewHolder generateViewHolder(int viewType) {
        LFRecyclerItemModel itemModel = null;
        for (LFRecyclerItemModel model : viewItemModels) {
            if (model.viewType == viewType) {
                itemModel = model;
            }
        }
        return itemModel == null ? null : itemModel.generateViewHolder();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return generateViewHolder(viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LFViewHolder) {
            viewItemModels.get(position).onBind(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewItemModels.get(position).viewType;
    }

    @Override
    public int getItemCount() {
        return viewItemModels.size();
    }
}
