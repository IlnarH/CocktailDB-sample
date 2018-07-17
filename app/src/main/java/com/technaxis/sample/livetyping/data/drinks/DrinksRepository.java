package com.technaxis.sample.livetyping.data.drinks;

import com.technaxis.sample.livetyping.business.model.Drink;
import com.technaxis.sample.livetyping.business.repository.IDrinksRepository;
import com.technaxis.sample.livetyping.data.api.CocktailDBApi;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class DrinksRepository implements IDrinksRepository {

    private CocktailDBApi cocktailDBApi;

    public DrinksRepository(CocktailDBApi cocktailDBApi) {
        this.cocktailDBApi = cocktailDBApi;
    }

    public static DrinksRepository create(CocktailDBApi cocktailDBApi) {
        return new DrinksRepository(cocktailDBApi);
    }

    @Override
    public Single<List<Drink>> getCategoryDrinks(String categoryName) {
        return cocktailDBApi.getCategoryDrinks(categoryName)
                .map(DrinksResponse::getDrinks)
                .doOnSuccess(drinks -> {
                    for (Drink drink : drinks) {
                        drink.setCategoryName(categoryName);
                    }
                });
    }
}
