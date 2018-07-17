package com.technaxis.sample.livetyping.data.api;

import com.technaxis.sample.livetyping.app.AppModule;
import com.technaxis.sample.livetyping.data.drink_categories.FilterSettings;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {

    void inject(FilterSettings filterSettings);
}
