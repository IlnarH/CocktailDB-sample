package com.technaxis.sample.livetyping.ui.drinks;

import com.technaxis.sample.livetyping.app.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CacheModule.class})
public interface CacheComponent {

    void inject(DrinksFragment fragment);
}
