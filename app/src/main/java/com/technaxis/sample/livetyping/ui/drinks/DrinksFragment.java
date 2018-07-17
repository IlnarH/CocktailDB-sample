package com.technaxis.sample.livetyping.ui.drinks;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.technaxis.sample.livetyping.app.MainApplication;
import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.data.drinks.DrinksCache;
import com.technaxis.sample.livetyping.ui.main.MainActivity;
import com.technaxis.sample.livetyping.ui.adapter.DrinkListAdapter;
import com.technaxis.sample.livetyping.ui.util.ListItemDecoration;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class DrinksFragment extends Fragment {

    private static final int NOT_VISIBLE_ITEMS_BEFORE_UPDATE = 10;

    private static final String LAYOUT_MANAGER_STATE_ATTR = "LAYOUT_MANAGER_STATE_ATTR";

    @Inject
    public DrinksCache drinksCache;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DrinkListAdapter adapter;

    private Disposable getDrinksDisposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private PublishSubject<Object> onScroll = PublishSubject.create();

    private MenuItem filterMenuItem;

    private Parcelable layoutManagerState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drinks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainApplication) getContext().getApplicationContext()).getCacheComponent().inject(this);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.fragment_drinks);
        filterMenuItem = toolbar.getMenu().findItem(R.id.filter);
        filterMenuItem.setOnMenuItemClickListener(menuItem -> {
            ((MainActivity) getActivity()).openFilter();
            return true;
        });
        updateFilterIcon();

        toolbar.setOnClickListener(view1 -> recyclerView.smoothScrollToPosition(0));

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view);

        swipeRefreshLayout.setOnRefreshListener(() -> refresh(false));
        setupRecyclerView();

        refresh(true);
    }

    private void setupRecyclerView() {
        recyclerView.addItemDecoration(new ListItemDecoration(
                ResourcesCompat.getDrawable(getResources(), R.drawable.list_items_divider, null)));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new DrinkListAdapter());

        compositeDisposable.add(onScroll.filter(o -> {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleCount = layoutManager.getChildCount();
            int totalCount = layoutManager.getItemCount();
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            return visibleCount + firstVisiblePosition >= totalCount - NOT_VISIBLE_ITEMS_BEFORE_UPDATE
                    && firstVisiblePosition >= 0 &&
                    (getDrinksDisposable == null || getDrinksDisposable.isDisposed());
        }).debounce(1000, TimeUnit.MILLISECONDS)
                .doOnNext(o -> getDrinksDisposable = drinksCache.fetchNextPage().toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(DrinksFragment.this::onDrinksLoadFinished)
                        .subscribe(adapter::append, DrinksFragment.this::onDrinksLoadFailed))
                .subscribe());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                onScroll.onNext(this);
            }
        });
    }

    private void updateFilterIcon() {
        if (drinksCache.filterSettings.isFilterEnabled()) {
            filterMenuItem.setIcon(R.drawable.filter_with_indicator);
        } else {
            filterMenuItem.setIcon(R.drawable.filter);
        }
    }

    public void refresh(boolean allowCached) {
        cancelLoad();
        updateFilterIcon();
        swipeRefreshLayout.setRefreshing(true);
        getDrinksDisposable = (allowCached ? drinksCache.getDrinks() : drinksCache.refresh())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(this::onDrinksLoadFinished)
                .subscribe((drinks) -> {
                    adapter.refresh(drinks);
                    if (layoutManagerState != null) {
                        recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerState);
                        layoutManagerState = null;
                    }
                }, this::onDrinksLoadFailed);
    }

    private void onDrinksLoadFinished() {
        getDrinksDisposable = null;
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onDrinksLoadFailed(Throwable throwable) {
        if (getContext() != null) {
            Toast.makeText(getContext(), R.string.error_drinks_no_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelLoad() {
        if (getDrinksDisposable != null && !getDrinksDisposable.isDisposed()) {
            getDrinksDisposable.dispose();
        }
        getDrinksDisposable = null;
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
        cancelLoad();
    }
}
