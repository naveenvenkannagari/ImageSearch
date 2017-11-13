package com.example.temp.imagesearch.di;

import android.app.Application;

import com.example.temp.imagesearch.data.source.common.DataSourceRouter;
import com.example.temp.imagesearch.data.source.local.ImageLocalDataService;
import com.example.temp.imagesearch.data.source.local.ImageSearchLocalDataSource;
import com.example.temp.imagesearch.data.source.remote.ImageSearchApiService;
import com.example.temp.imagesearch.data.source.remote.ImageSearchRemoteDataSource;
import com.example.temp.imagesearch.di.scope.ApplicationScope;


import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;

/**
 * Created by temp on 11/11/17.
 */

@Module
public class DataModule {


    public static final int REALM_SCHEMA = 1;
    public static final String REALM_FILE_NAME = "ImageSearch.realm";

    @Provides
    @ApplicationScope
    public ImageSearchApiService provideImageApiService(Retrofit retrofit) {
        return retrofit.create(ImageSearchApiService.class);
    }


    @Provides
    @ApplicationScope
    public ImageSearchRemoteDataSource provideImageSearchRemoteDataSource(ImageSearchApiService feedAPIService) {
        return new ImageSearchRemoteDataSource(feedAPIService);
    }


    @Provides
    @ApplicationScope
    public Realm getRealm(Application application) {
        Realm.init(application);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().
                name(REALM_FILE_NAME).schemaVersion(REALM_SCHEMA).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }


    @Provides
    @ApplicationScope
    public ImageLocalDataService getImageLocalDataService(Realm realm) {
        return new ImageLocalDataService(realm);
    }

    @Provides
    @ApplicationScope
    public DataSourceRouter provideDataService(ImageSearchRemoteDataSource imageSearchRemoteDataSource,
                                               ImageSearchLocalDataSource imageSearchLocalDataSource) {
        return new DataSourceRouter(imageSearchRemoteDataSource, imageSearchLocalDataSource);
    }


}
