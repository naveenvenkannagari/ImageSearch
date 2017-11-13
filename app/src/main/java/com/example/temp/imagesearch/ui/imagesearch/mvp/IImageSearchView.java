package com.example.temp.imagesearch.ui.imagesearch.mvp;

import com.example.temp.imagesearch.data.models.Photo;

import java.util.List;

/**
 * Created by temp on 11/11/17.
 */

public interface IImageSearchView {

    void showPhotoList(List<Photo> photoList);

    void showProgressView();

    void hideProgressView();

    void showErrorMessage();

    void showNoImagesFoundMessage();
}
