package com.example.temp.imagesearch.data.source.local;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.IDataSource;
import com.example.temp.imagesearch.data.source.remote.ImageSearchApiService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by temp on 10/11/17.
 */

public class ImageSearchLocalDataSource implements IDataSource {

    ImageLocalDataService imageLocalDataService;


    @Inject
    public ImageSearchLocalDataSource(ImageLocalDataService imageLocalDataService) {
        this.imageLocalDataService = imageLocalDataService;
    }

    @Override
    public Observable<ImageSearchResponse> getImagesBasedonSearchText(String searchText) {
        return null;
    }

    @Override
    public void onImageSaveToFavourite(Photo photo) {
        imageLocalDataService.addFavouritePhoto(photo);
    }

    @Override
    public List<Photo> getAllFavouritesImages() {
        return imageLocalDataService.getAllFavourites();
    }

    @Override
    public void removeImageAsFavourite(String photoId) {
        imageLocalDataService.removeFavouritePhoto(photoId);
    }
}
