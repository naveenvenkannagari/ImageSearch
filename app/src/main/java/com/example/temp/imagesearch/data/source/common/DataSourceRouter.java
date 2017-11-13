package com.example.temp.imagesearch.data.source.common;

import android.support.annotation.NonNull;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.local.ImageSearchLocalDataSource;
import com.example.temp.imagesearch.data.source.remote.ImageSearchRemoteDataSource;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by temp on 10/11/17.
 */

public class DataSourceRouter implements IDataSource {

    @NonNull
    private IDataSource mImageSearchRemoteDataSource;

    @NonNull
    private IDataSource mImageSearchLocalDataSource;


    @Inject
    public DataSourceRouter(ImageSearchRemoteDataSource imageSearchRemoteDataSource,
                            ImageSearchLocalDataSource imageSearchLocalDataSource) {
        mImageSearchRemoteDataSource = imageSearchRemoteDataSource;
        mImageSearchLocalDataSource = imageSearchLocalDataSource;
    }

    @Override
    public Observable<ImageSearchResponse> getImagesBasedonSearchText(String searchText) {
        return mImageSearchRemoteDataSource.getImagesBasedonSearchText(searchText);
    }

    @Override
    public void onImageSaveToFavourite(Photo photo) {
         mImageSearchLocalDataSource.onImageSaveToFavourite(photo);
    }

    @Override
    public List<Photo> getAllFavouritesImages() {
        return mImageSearchLocalDataSource.getAllFavouritesImages();
    }

    @Override
    public void removeImageAsFavourite(String photoId) {
         mImageSearchLocalDataSource.removeImageAsFavourite(photoId);
    }
}
