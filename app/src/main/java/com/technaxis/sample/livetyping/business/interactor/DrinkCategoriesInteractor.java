package com.technaxis.sample.livetyping.business.interactor;

import com.technaxis.sample.livetyping.util.SchedulersProvider;
import com.technaxis.sample.livetyping.business.model.DrinkCategory;
import com.technaxis.sample.livetyping.business.repository.IDrinkCategoriesRepository;

import java.util.List;

import io.reactivex.Single;

public class DrinkCategoriesInteractor implements IDrinkCategoriesInteractor {

    private IDrinkCategoriesRepository drinkCategoriesRepository;
    private SchedulersProvider schedulersProvider;

    public DrinkCategoriesInteractor(IDrinkCategoriesRepository drinkCategoriesRepository, SchedulersProvider schedulersProvider) {
        this.drinkCategoriesRepository = drinkCategoriesRepository;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public Single<List<DrinkCategory>> getDrinkCategories() {
        return drinkCategoriesRepository.getDrinkCategories()
                .subscribeOn(schedulersProvider.io());
    }
}
