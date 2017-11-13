package com.example.temp.imagesearch.di;

import android.content.Context;

import com.example.temp.imagesearch.data.source.remote.ImageSearchApiService;
import com.example.temp.imagesearch.di.scope.ApplicationScope;
import com.example.temp.imagesearch.ui.imagefavourite.ImageFavouriteFragment;
import com.example.temp.imagesearch.ui.imagesearch.ImageSearchFragment;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by temp on 10/11/17.
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class})
public interface AppComponent {

    Context getContext();

    Retrofit getRetrofit();

    ImageSearchApiService getImageSearchApiService();

    void inject(ImageSearchFragment imageSearchFragment);

    void inject(ImageFavouriteFragment imageFavouriteFragment);
}
