package com.technaxis.sample.livetyping.business.repository;

import com.technaxis.sample.livetyping.business.model.Drink;

import java.util.List;

import io.reactivex.Single;

public interface IDrinksRepository {

    Single<List<Drink>> getCategoryDrinks(String categoryName);
}
