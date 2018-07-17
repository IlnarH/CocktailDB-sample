package com.technaxis.sample.livetyping.business.interactor;

import com.technaxis.sample.livetyping.business.model.DrinkCategory;

import java.util.List;

import io.reactivex.Single;

public interface IDrinkCategoriesInteractor {

    Single<List<DrinkCategory>> getDrinkCategories();
}
