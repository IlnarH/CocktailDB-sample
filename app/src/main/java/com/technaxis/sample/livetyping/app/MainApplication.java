package com.technaxis.sample.livetyping.app;

import android.app.Application;

import com.technaxis.sample.livetyping.data.api.ApiComponent;
import com.technaxis.sample.livetyping.data.api.ApiModule;
import com.technaxis.sample.livetyping.data.api.DaggerApiComponent;
import com.technaxis.sample.livetyping.ui.drinks.CacheComponent;
import com.technaxis.sample.livetyping.ui.drinks.CacheModule;
import com.technaxis.sample.livetyping.ui.drinks.DaggerCacheComponent;
import com.technaxis.sample.livetyping.ui.filter.DaggerFilterComponent;
import com.technaxis.sample.livetyping.ui.filter.FilterComponent;
import com.technaxis.sample.livetyping.ui.filter.FilterModule;

public class MainApplication extends Application {

    private ApiComponent apiComponent;
    private FilterComponent filterComponent;
    private CacheComponent cacheComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        AppModule appModule = new AppModule(this);
        apiComponent = DaggerApiComponent.builder()
                .appModule(appModule)
                .apiModule(new ApiModule())
                .build();

        filterComponent = DaggerFilterComponent.builder()
                .appModule(appModule)
                .filterModule(new FilterModule())
                .build();

        cacheComponent = DaggerCacheComponent.builder()
                .appModule(appModule)
                .cacheModule(new CacheModule())
                .build();
    }

    public ApiComponent getApiComponent() {
        return apiComponent;
    }

    public FilterComponent getFilterComponent() {
        return filterComponent;
    }

    public CacheComponent getCacheComponent() {
        return cacheComponent;
    }
}
