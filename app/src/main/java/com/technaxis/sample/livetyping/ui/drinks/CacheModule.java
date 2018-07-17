package com.technaxis.sample.livetyping.ui.drinks;

import android.app.Application;

import com.technaxis.sample.livetyping.data.drinks.DrinksCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Provides
    @Singleton
    public DrinksCache provideDrinkCategoriesCache(Application application) {
        return new DrinksCache(application);
    }
}
