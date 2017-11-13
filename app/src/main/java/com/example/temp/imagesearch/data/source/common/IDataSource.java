package com.example.temp.imagesearch.data.source.common;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;

import java.util.List;

import rx.Observable;

/**
 * Created by temp on 10/11/17.
 */

public interface IDataSource {

    Observable<ImageSearchResponse> getImagesBasedonSearchText(String searchText);

    void onImageSaveToFavourite(Photo photo);

    List<Photo> getAllFavouritesImages();

    void removeImageAsFavourite(String photoId);
}
