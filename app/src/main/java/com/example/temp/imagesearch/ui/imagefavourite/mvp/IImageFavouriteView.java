package com.example.temp.imagesearch.ui.imagefavourite.mvp;

import com.example.temp.imagesearch.data.models.Photo;

import java.util.List;

/**
 * Created by temp on 12/11/17.
 */

public interface IImageFavouriteView {

    void showPhotoList(List<Photo> photoList);

}
