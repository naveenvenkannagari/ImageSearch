package com.example.temp.imagesearch.ui.imagesearch.mvp;

import android.support.annotation.NonNull;

import com.example.temp.imagesearch.ui.base.BasePresenter;
import com.example.temp.imagesearch.data.models.ImageSearchResponse;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.source.common.DataSourceRouter;
import com.example.temp.imagesearch.domain.ImageSearchUseCaseController;
import com.example.temp.imagesearch.utils.RxUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by temp on 11/11/17.
 */

public class ImageSearchPresenter implements BasePresenter {

    @NonNull
    private CompositeSubscription mSubscriptions;

    private ImageSearchUseCaseController mImageSearchUsecaseController;
    private IImageSearchView mImageSearchView;


    @Inject
    public ImageSearchPresenter(DataSourceRouter dataSourceRouter) {
        mImageSearchUsecaseController = new ImageSearchUseCaseController(dataSourceRouter);
        mSubscriptions  = new CompositeSubscription();
    }

    public void setView(IImageSearchView mImageSearchView) {
        this.mImageSearchView = mImageSearchView;
    }


    public void onSearchImage(String query) {
        mImageSearchView.showProgressView();
        Observable<ImageSearchResponse> response =
                mImageSearchUsecaseController.getImagesBasedonSearchText(query);
        RxUtils.addToCompositeSubscription(mSubscriptions, response.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                .mainThread()).subscribe(new Subscriber<ImageSearchResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mImageSearchView.hideProgressView();
                mImageSearchView.showErrorMessage();
                e.printStackTrace();
            }

            @Override
            public void onNext(ImageSearchResponse imageSearchResponse) {
                mImageSearchView.hideProgressView();
                if (imageSearchResponse != null && imageSearchResponse.getPhotos() != null) {
                    if (imageSearchResponse.getPhotos().getPhoto().size() > 0) {
                        mImageSearchView.showPhotoList(imageSearchResponse.getPhotos().getPhoto());
                    } else {
                        mImageSearchView.showNoImagesFoundMessage();
                    }
                }
            }
        }));
    }

    public void onImageSetToFavourite(Photo photo) {
        mImageSearchUsecaseController.onImageSetToFavourite(photo);
    }


    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mSubscriptions.clear();
    }


}
