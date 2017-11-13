package com.example.temp.imagesearch;

import android.app.Application;

import com.example.temp.imagesearch.di.AppComponent;
import com.example.temp.imagesearch.di.ApplicationModule;
import com.example.temp.imagesearch.di.DaggerAppComponent;
import com.example.temp.imagesearch.di.DataModule;
import com.example.temp.imagesearch.di.NetworkModule;
import com.example.temp.imagesearch.utils.Constants;

/**
 * Created by temp on 10/11/17.
 */

public class ImageSearchApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }


    private void initializeDependencyInjector(){
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(Constants.SERVICE_END_POINT))
                .dataModule(new DataModule())
                .build();
    }


    public AppComponent getAppComponent(){
        return mAppComponent;
    }
}
