package com.example.temp.imagesearch.di;

import com.example.temp.imagesearch.data.source.remote.ImageSearchApiService;
import com.example.temp.imagesearch.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by temp on 10/11/17.
 */

@Module
public class NetworkModule {

    String mBaseUrl;


    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .baseUrl(mBaseUrl)
                .build();
    }

}
