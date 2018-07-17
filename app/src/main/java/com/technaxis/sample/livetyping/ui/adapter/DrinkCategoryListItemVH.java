package com.technaxis.sample.livetyping.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;

public class DrinkCategoryListItemVH extends RecyclerView.ViewHolder {

    public final TextView categoryName;
    public final View selection;

    public DrinkCategoryListItemVH(View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.category_name);
        selection = itemView.findViewById(R.id.selection);
    }

    public void bind(DrinkCategory category, boolean selected) {
        categoryName.setText(category.getName());
        selection.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
    }
}
