package com.technaxis.sample.livetyping.ui.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * <br><br>Created by downf on 16.08.2017.
 */

public class ListBottomSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public ListBottomSpaceItemDecoration(Context context) {
        this(context, 24);
    }

    public ListBottomSpaceItemDecoration(Context context, int spacingDp) {
        this.spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spacingDp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        if (position == parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = spacing;
        } else {
            outRect.bottom = 0;
        }
    }
}