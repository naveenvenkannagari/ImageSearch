package com.example.temp.imagesearch.di;

import android.app.Application;
import android.content.Context;

import com.example.temp.imagesearch.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by temp on 10/11/17.
 */

@Module
public class ApplicationModule {

    Application mImageSearchApplication;
    Context mContext;

    public ApplicationModule(Application application) {
        mImageSearchApplication = application;
        mContext = application.getApplicationContext();
    }


    @Provides
    @ApplicationScope
    Application providesApplication() {
        return mImageSearchApplication;
    }

    @Provides
    @ApplicationScope
    Context providesContext() {
        return mContext;
    }


}
