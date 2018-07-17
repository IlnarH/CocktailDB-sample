package com.technaxis.sample.livetyping.business.interactor;

import com.technaxis.sample.livetyping.util.SchedulersProvider;
import com.technaxis.sample.livetyping.business.model.Drink;
import com.technaxis.sample.livetyping.business.repository.IDrinksRepository;

import java.util.List;

import io.reactivex.Single;

public class DrinksInteractor implements IDrinksInteractor {

    private IDrinksRepository drinksRepository;
    private SchedulersProvider schedulersProvider;

    public DrinksInteractor(IDrinksRepository drinksRepository, SchedulersProvider schedulersProvider) {
        this.drinksRepository = drinksRepository;
        this.schedulersProvider = schedulersProvider;
    }

    @Override
    public Single<List<Drink>> getCategoryDrinks(String categoryName) {
        return drinksRepository.getCategoryDrinks(categoryName)
                .subscribeOn(schedulersProvider.io());
    }
}
