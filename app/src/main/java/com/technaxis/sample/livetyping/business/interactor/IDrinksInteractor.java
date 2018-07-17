package com.technaxis.sample.livetyping.business.interactor;

import com.technaxis.sample.livetyping.business.model.Drink;

import java.util.List;

import io.reactivex.Single;

public interface IDrinksInteractor {

    Single<List<Drink>> getCategoryDrinks(String categoryName);
}
