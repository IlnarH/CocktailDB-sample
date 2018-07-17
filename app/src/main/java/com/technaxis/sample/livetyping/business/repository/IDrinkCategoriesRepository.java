package com.technaxis.sample.livetyping.business.repository;

import com.technaxis.sample.livetyping.business.model.DrinkCategory;

import java.util.List;

import io.reactivex.Single;

public interface IDrinkCategoriesRepository {

    Single<List<DrinkCategory>> getDrinkCategories();
}
