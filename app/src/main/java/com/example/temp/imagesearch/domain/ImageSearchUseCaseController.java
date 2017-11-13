package com.example.temp.imagesearch.domain;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.DataSourceRouter;

import rx.Observable;

/**
 * Created by temp on 11/11/17.
 */

public class ImageSearchUseCaseController {
    private DataSourceRouter dataSourceRouter;

    public ImageSearchUseCaseController(DataSourceRouter dataSourceRouter) {
        this.dataSourceRouter = dataSourceRouter;
    }

    public Observable<ImageSearchResponse> getImagesBasedonSearchText(String searchText) {
        return dataSourceRouter.getImagesBasedonSearchText(searchText);
    }

    public void onImageSetToFavourite(Photo photo) {
        dataSourceRouter.onImageSaveToFavourite(photo);
    }
}
