package com.technaxis.sample.livetyping.data.drink_categories;

import com.technaxis.sample.livetyping.business.model.DrinkCategory;
import com.technaxis.sample.livetyping.business.repository.IDrinkCategoriesRepository;
import com.technaxis.sample.livetyping.data.api.CocktailDBApi;

import java.util.List;

import io.reactivex.Single;

public class DrinkCategoriesRepository implements IDrinkCategoriesRepository {

    private CocktailDBApi cocktailDBApi;

    public DrinkCategoriesRepository(CocktailDBApi cocktailDBApi) {
        this.cocktailDBApi = cocktailDBApi;
    }

    public static DrinkCategoriesRepository create(CocktailDBApi cocktailDBApi) {
        return new DrinkCategoriesRepository(cocktailDBApi);
    }

    @Override
    public Single<List<DrinkCategory>> getDrinkCategories() {
        return cocktailDBApi.getDrinkCategories()
                .map(DrinkCategoriesResponse::getCategories);
    }
}
