package com.example.temp.imagesearch.ui.imagefavourite.mvp;

import com.example.temp.imagesearch.ui.base.BasePresenter;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.DataSourceRouter;
import com.example.temp.imagesearch.domain.ImageFavouriteUsecaseController;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by temp on 12/11/17.
 */

public class ImageFavouritePresenter implements BasePresenter {

    private ImageFavouriteUsecaseController mImageFavouriteUsecaseController;
    private IImageFavouriteView iImageFavouriteView;

    @Inject
    public ImageFavouritePresenter(DataSourceRouter dataSourceRouter) {
        mImageFavouriteUsecaseController = new ImageFavouriteUsecaseController(dataSourceRouter);
    }

    public void setView(IImageFavouriteView imageFavouriteView) {
        iImageFavouriteView = imageFavouriteView;
    }

    public void getAllFavourites() {
        List<Photo> favouritePhotos = mImageFavouriteUsecaseController.getAllFavoriteImages();
        iImageFavouriteView.showPhotoList(favouritePhotos);
    }

    public void removePhotoFromFavouriteList(Photo photo) {
        mImageFavouriteUsecaseController.removeImageAsFavourite(photo.getId());
    }

    @Override
    public void onStart() {
        //NA
    }

    @Override
    public void onStop() {
        //NA
    }
}
