package com.technaxis.sample.livetyping.ui.filter;

import com.technaxis.sample.livetyping.app.AppModule;
import com.technaxis.sample.livetyping.data.drinks.DrinksCache;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FilterModule.class})
public interface FilterComponent {

    void inject(DrinksCache drinksCache);

    void inject(CategoriesFilterFragment categoriesFilterFragment);
}
