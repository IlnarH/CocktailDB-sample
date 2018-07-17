package com.technaxis.sample.livetyping.data.drinks;

import android.app.Application;

import com.technaxis.sample.livetyping.app.MainApplication;
import com.technaxis.sample.livetyping.util.SchedulersProvider;
import com.technaxis.sample.livetyping.business.interactor.DrinksInteractor;
import com.technaxis.sample.livetyping.business.model.Drink;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;
import com.technaxis.sample.livetyping.data.drink_categories.FilterSettings;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class DrinksCache {

    @Inject
    public FilterSettings filterSettings;

    private final List<Drink> drinks = new ArrayList<>();

    public DrinksCache(Application application) {
        ((MainApplication) application).getFilterComponent().inject(this);
    }

    public Single<List<Drink>> refresh() {
        drinks.clear();
        Single<List<DrinkCategory>> categories;
        if (filterSettings.isFilterEnabled()) {
            categories = Single.just(filterSettings.getSelectedCategories());
        } else {
            categories = filterSettings.getCategories();
        }
        return categories
                .flatMap((Function<List<DrinkCategory>, SingleSource<String>>) categoryList ->
                        Single.just(categoryList.get(0).getName()))
                .flatMap((category) -> new DrinksInteractor(DrinksRepository.create(filterSettings.cocktailDBApi),
                                new SchedulersProvider()).getCategoryDrinks(category))
                .doOnSuccess(drinks::addAll);
    }

    public Single<List<Drink>> fetchNextPage() {
        Single<List<DrinkCategory>> categories;
        if (filterSettings.isFilterEnabled()) {
            categories = Single.just(filterSettings.getSelectedCategories());
        } else {
            categories = filterSettings.getCategories();
        }
        return categories
                .flatMap((Function<List<DrinkCategory>, SingleSource<String>>) categoryList -> {
                    String previousCategory = drinks.isEmpty() ? null :
                            drinks.get(drinks.size() - 1).getCategoryName();
                    boolean pickNext = previousCategory == null;
                    for (DrinkCategory drinkCategory : categoryList) {
                        if (pickNext) {
                            return Single.just(drinkCategory.getName());
                        }
                        if (drinkCategory.getName().equals(previousCategory)) {
                            pickNext = true;
                        }
                    }
                    return Single.just("");
                })
                .flatMap((categoryName) -> categoryName.isEmpty() ? Single.just(new ArrayList<Drink>()) :
                        new DrinksInteractor(DrinksRepository.create(filterSettings.cocktailDBApi),
                                new SchedulersProvider()).getCategoryDrinks(categoryName))
                .doOnSuccess(drinks::addAll);
    }

    public Single<List<Drink>> getDrinks() {
        if (drinks.isEmpty()) {
            return refresh();
        }
        return Single.just(drinks);
    }
}
