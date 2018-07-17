package com.technaxis.sample.livetyping.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;

import java.util.ArrayList;
import java.util.List;

public class DrinkCategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private final OnSelectedItemsChanged onSelectedItemsChanged;

    private List<DrinkCategory> items;
    private List<DrinkCategory> selected;

    public DrinkCategoryListAdapter(OnSelectedItemsChanged onSelectedItemsChanged) {
        this.onSelectedItemsChanged = onSelectedItemsChanged;
    }

    public void setItems(List<DrinkCategory> items, List<DrinkCategory> selected) {
        this.items = items;
        this.selected = new ArrayList<>();
        this.selected.addAll(selected);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new DrinkCategoryListItemVH(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_drink_category_list_item, parent, false));
        }
        return new RecyclerViewFooterVH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_empty_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            DrinkCategory item = getItem(position);
            ((DrinkCategoryListItemVH) holder).bind(item, selected.contains(item));
            holder.itemView.setOnClickListener(view -> {
                if (selected.contains(item)) {
                    selected.remove(item);
                } else {
                    selected.add(item);
                }
                notifyItemChanged(position);
                onSelectedItemsChanged.onSelectedItemsChanged();
            });
        }
    }

    private DrinkCategory getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position < items.size() ? TYPE_ITEM : TYPE_FOOTER;
    }

    public List<DrinkCategory> getSelected() {
        return selected;
    }

    public interface OnSelectedItemsChanged {
        void onSelectedItemsChanged();
    }
}
