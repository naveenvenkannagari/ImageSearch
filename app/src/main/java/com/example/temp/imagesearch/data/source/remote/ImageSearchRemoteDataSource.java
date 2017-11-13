package com.example.temp.imagesearch.data.source.remote;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.IDataSource;
import com.example.temp.imagesearch.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by temp on 10/11/17.
 */

public class ImageSearchRemoteDataSource implements IDataSource {

    private ImageSearchApiService mImageSearchApiService;

    @Inject
    public ImageSearchRemoteDataSource(ImageSearchApiService imageSearchApiService) {
        this.mImageSearchApiService = imageSearchApiService;
    }

    @Override
    public Observable<ImageSearchResponse> getImagesBasedonSearchText(String searchText) {
        return mImageSearchApiService.
                getImagesBasedonSearchText(Constants.API_KEY, searchText, "json", 1);
    }

    @Override
    public void onImageSaveToFavourite(Photo photo) {
        //NA
    }

    @Override
    public List<Photo> getAllFavouritesImages() {
        return null;
        //NA
    }

    @Override
    public void removeImageAsFavourite(String photoId) {
        //NA
    }
}
