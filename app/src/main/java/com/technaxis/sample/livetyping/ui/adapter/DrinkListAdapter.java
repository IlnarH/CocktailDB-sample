package com.technaxis.sample.livetyping.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.business.model.Drink;

import java.util.ArrayList;
import java.util.List;

public class DrinkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private final List<Drink> items = new ArrayList<>();

    /**
     * Indicates if all items are loaded from source, used for displaying footer with progress bar
     */
    private boolean completeList = false;

    public void refresh(List<Drink> drinks) {
        completeList = false;
        items.clear();
        items.addAll(drinks);
        notifyDataSetChanged();
    }

    public void append(List<Drink> drinks) {
        if (drinks.isEmpty()) {
            completeList = true;
            notifyItemChanged(getItemCount() - 1);
        } else {
            int size = items.size();
            items.addAll(drinks);
            notifyItemRangeInserted(size, drinks.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new DrinkListItemVH(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_drink_list_item, parent, false));
        }
        return new DrinkCategoryListItemVH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_progress_bar_footer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ((DrinkListItemVH) holder).bind(getItem(position), getItem(position - 1));
        } else {
            holder.itemView.setVisibility(completeList || items.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private Drink getItem(int position) {
        if (position < 0 || position >= items.size()) {
            return null;
        }
        return items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position < items.size() ? TYPE_ITEM : TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}
