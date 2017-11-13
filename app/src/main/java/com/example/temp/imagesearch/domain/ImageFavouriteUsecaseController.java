package com.example.temp.imagesearch.domain;

import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.DataSourceRouter;

import java.util.List;

import rx.Observable;

/**
 * Created by temp on 12/11/17.
 */

public class ImageFavouriteUsecaseController {

    private DataSourceRouter dataSourceRouter;

    public ImageFavouriteUsecaseController(DataSourceRouter dataSourceRouter) {
        this.dataSourceRouter = dataSourceRouter;
    }

    public List<Photo> getAllFavoriteImages(){
        return dataSourceRouter.getAllFavouritesImages();
    }

    public void removeImageAsFavourite(String photoId){
        dataSourceRouter.removeImageAsFavourite(photoId);
    }
}
