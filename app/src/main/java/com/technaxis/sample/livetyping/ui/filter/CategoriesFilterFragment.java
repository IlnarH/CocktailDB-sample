package com.technaxis.sample.livetyping.ui.filter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technaxis.sample.livetyping.app.MainApplication;
import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.util.Util;
import com.technaxis.sample.livetyping.data.drink_categories.FilterSettings;
import com.technaxis.sample.livetyping.ui.main.MainActivity;
import com.technaxis.sample.livetyping.ui.adapter.DrinkCategoryListAdapter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CategoriesFilterFragment extends Fragment {

    private static final String LAYOUT_MANAGER_STATE_ATTR = "LAYOUT_MANAGER_STATE_ATTR";

    @Inject
    public FilterSettings filterSettings;

    private View apply;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private DrinkCategoryListAdapter adapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Parcelable layoutManagerState;

    private DrinkCategoryListAdapter.OnSelectedItemsChanged onSelectedItemsChanged =
            () -> apply.setEnabled(!Util.contentsEqual(filterSettings.getSelectedCategories(), adapter.getSelected()));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainApplication) getContext().getApplicationContext()).getFilterComponent().inject(this);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(view1 -> getActivity().onBackPressed());

        apply = view.findViewById(R.id.apply);
        apply.setEnabled(false);
        apply.setOnClickListener(view12 -> {
            filterSettings.setSelectedCategories(adapter.getSelected());
            ((MainActivity) getActivity()).applyFilter();
        });

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new DrinkCategoryListAdapter(onSelectedItemsChanged));

        swipeRefreshLayout.setRefreshing(true);
        compositeDisposable.add(filterSettings.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::onCategoriesLoadFinished)
                .subscribe((categories) -> {
                            adapter.setItems(categories, filterSettings.getSelectedCategories());
                            if (layoutManagerState != null) {
                                recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerState);
                                layoutManagerState = null;
                            }
                        },
                        this::onCategoriesLoadFailed));
    }

    private void onCategoriesLoadFinished() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onCategoriesLoadFailed(Throwable throwable) {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.error_categories_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(LAYOUT_MANAGER_STATE_ATTR, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE_ATTR);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
