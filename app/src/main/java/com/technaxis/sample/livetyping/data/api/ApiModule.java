package com.technaxis.sample.livetyping.data.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.JacksonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class ApiModule {

    private static final String COCKTAIL_DB_BASE_API_URL = "https://www.thecocktaildb.com";

    public ApiModule() {
    }

    @Provides
    @Singleton
    JacksonConverterFactory provideJacksonConverterFactory() {
        return JacksonConverterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    CocktailDBApi provideCocktailDBApi(JacksonConverterFactory jacksonConverterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(COCKTAIL_DB_BASE_API_URL)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(CocktailDBApi.class);
    }
}
