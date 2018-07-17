package com.technaxis.sample.livetyping.ui.filter;

import android.app.Application;

import com.technaxis.sample.livetyping.data.drink_categories.FilterSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterModule {

    @Provides
    @Singleton
    public FilterSettings provideFilterSettings(Application application) {
        return new FilterSettings(application);
    }
}
