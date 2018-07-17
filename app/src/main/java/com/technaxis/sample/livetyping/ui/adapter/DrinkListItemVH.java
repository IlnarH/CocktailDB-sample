package com.technaxis.sample.livetyping.ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.business.model.Drink;
import com.technaxis.sample.livetyping.ui.util.ColorGenerator;

public class DrinkListItemVH extends RecyclerView.ViewHolder {

    public final TextView categoryHeader;
    public final TextView drinkName;
    public final ImageView image;

    public DrinkListItemVH(View itemView) {
        super(itemView);

        categoryHeader = itemView.findViewById(R.id.category_header);
        drinkName = itemView.findViewById(R.id.drink_name);
        image = itemView.findViewById(R.id.image);
    }

    public void bind(Drink drink, Drink prevItem) {
        categoryHeader.setText(drink.getCategoryName());
        if (prevItem != null && prevItem.getCategoryName().equals(drink.getCategoryName())) {
            categoryHeader.setVisibility(View.GONE);
        } else {
            categoryHeader.setVisibility(View.VISIBLE);
        }

        drinkName.setText(drink.getName());

        Glide.with(itemView)
                .setDefaultRequestOptions(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(new ColorDrawable(ColorGenerator.MATERIAL.getColor(drink.getImageUrl()))))
                .load(drink.getImageUrl())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(image);
    }
}
