package com.technaxis.sample.livetyping.data.api;

import com.technaxis.sample.livetyping.data.drink_categories.DrinkCategoriesResponse;
import com.technaxis.sample.livetyping.data.drinks.DrinksResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CocktailDBApi {

    @GET("/api/json/v1/1/list.php?c=list")
    Single<DrinkCategoriesResponse> getDrinkCategories();

    @GET("/api/json/v1/1/filter.php")
    Single<DrinksResponse> getCategoryDrinks(@Query("c") String categoryName);
}
