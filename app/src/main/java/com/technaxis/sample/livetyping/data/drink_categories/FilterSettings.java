package com.technaxis.sample.livetyping.data.drink_categories;

import android.app.Application;

import com.technaxis.sample.livetyping.app.MainApplication;
import com.technaxis.sample.livetyping.util.SchedulersProvider;
import com.technaxis.sample.livetyping.business.interactor.DrinkCategoriesInteractor;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;
import com.technaxis.sample.livetyping.data.api.CocktailDBApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class FilterSettings {

    @Inject
    public CocktailDBApi cocktailDBApi;

    private Single<List<DrinkCategory>> categoriesObservable;
    private List<DrinkCategory> categories;
    private List<DrinkCategory> selectedCategories = new ArrayList<>();

    public FilterSettings(Application application) {
        ((MainApplication) application).getApiComponent().inject(this);
    }

    public Single<List<DrinkCategory>> getCategories() {
        if (categories == null) {
            if (categoriesObservable == null) {
                return categoriesObservable = new DrinkCategoriesInteractor(
                        DrinkCategoriesRepository.create(cocktailDBApi), new SchedulersProvider())
                        .getDrinkCategories()
                        .doOnSuccess(drinkCategories -> categories = drinkCategories)
                        .doFinally(() -> categoriesObservable = null);
            } else {
                return categoriesObservable;
            }
        }
        return Single.just(categories);
    }

    public List<DrinkCategory> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<DrinkCategory> selectedCategories) {
        Collections.sort(selectedCategories, (categoryA, categoryB) ->
                Integer.compare(categories.indexOf(categoryA), categories.indexOf(categoryB)));
        this.selectedCategories = selectedCategories;
    }

    public boolean isFilterEnabled() {
        return !selectedCategories.isEmpty();
    }
}
