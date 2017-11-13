package com.example.temp.imagesearch.ui.imagefavourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.temp.imagesearch.ImageSearchApplication;
import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.ui.imagefavourite.mvp.IImageFavouriteView;
import com.example.temp.imagesearch.ui.imagefavourite.mvp.ImageFavouritePresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by temp on 11/11/17.
 */

public class ImageFavouriteFragment extends Fragment implements IImageFavouriteView, ImageFavouriteAdapter.ImageSelectedListener {

    private static final int SPAN_COUNT = 3;
    @Inject
    ImageFavouritePresenter imageFavouritePresenter;

    @BindView(R.id.favourite_list)
    RecyclerView favouriteListView;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_image_favourite, container, false);
        unbinder = ButterKnife.bind(this, view);
        initDI();
        imageFavouritePresenter.setView(this);
        return view;
    }

    private void initUI() {
        imageFavouritePresenter.getAllFavourites();
    }

    private void initDI() {
        ((ImageSearchApplication) getActivity().getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            initUI();
        }
    }


    @Override
    public void showPhotoList(List<Photo> photoList) {
        if (photoList.size() > 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
            favouriteListView.setLayoutManager(gridLayoutManager);
            ImageFavouriteAdapter imageFavouriteAdapter = new ImageFavouriteAdapter(photoList, getActivity(), this);
            favouriteListView.setAdapter(imageFavouriteAdapter);
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_favorite_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onImageLongPressClick(Photo photo) {
        imageFavouritePresenter.removePhotoFromFavouriteList(photo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
