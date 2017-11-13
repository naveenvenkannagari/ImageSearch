package com.example.temp.imagesearch.ui.imagesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.temp.imagesearch.ImageSearchApplication;
import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.ui.customcomponents.RxSearch;
import com.example.temp.imagesearch.ui.imagesearch.mvp.IImageSearchView;
import com.example.temp.imagesearch.ui.imagesearch.mvp.ImageSearchPresenter;
import com.example.temp.imagesearch.utils.Utility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by temp on 11/11/17.
 */

public class ImageSearchFragment extends Fragment implements IImageSearchView, ImageSearchAdapter.ImageSelectedListener {

    private final int SPAN_COUNT = 3;
    private final int DEBOUNCE_DELAY_TIME = 250;

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.images_list)
    RecyclerView mImageRecyclerView;

    @BindView(R.id.grid_text)
    TextView mGridLabel;

    @BindView(R.id.list_text)
    TextView mListLabel;

    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @OnClick(R.id.grid_text)
    public void onGridLabelClicked() {
        mGridLabel.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mGridLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        mListLabel.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mListLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
    }

    @OnClick(R.id.list_text)
    public void onListLabelClicked() {
        mListLabel.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        mListLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        mGridLabel.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mGridLabel.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
    }

    @Inject
    ImageSearchPresenter mImageSearchPresenter;

    private RecyclerView.LayoutManager mLayoutManager;
    private ImageSearchAdapter mImageSearchAdapter;
    private Unbinder mUnbinder;
    private List<Photo> imagesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_search, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initDependencyInjection();
        initUI();
        return view;
    }

    private void initUI() {
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (imagesList != null && imagesList.size() > 0) {
                    imagesList.clear();
                }
                return false;
            }
        });
        mImageSearchAdapter = new ImageSearchAdapter(getActivity(), this);
        mImageSearchPresenter.setView(this);
        RxSearch.fromSearchView(mSearchView)
                .debounce(DEBOUNCE_DELAY_TIME, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    getImagesBasedOnSearchText(query);
                });
        //setting default type to grid type
        onGridLabelClicked();
    }


    private void initDependencyInjection() {
        ((ImageSearchApplication) getActivity().getApplicationContext()).getAppComponent().inject(this);
    }

    private void getImagesBasedOnSearchText(String query) {
        mImageSearchPresenter.onSearchImage(query);
    }

    @Override
    public void showPhotoList(List<Photo> photoList) {
        imagesList = photoList;
        mImageSearchAdapter.updateDataSet(imagesList);
        mImageRecyclerView.setAdapter(mImageSearchAdapter);
    }

    @Override
    public void showProgressView() {
        Utility.showProgressDialog(getActivity(), getString(R.string.progress_dialog_message), false);
    }

    @Override
    public void hideProgressView() {
        Utility.hideProgressDialog();
    }

    @Override
    public void showErrorMessage() {
        String message = getString(R.string.error_message);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoImagesFoundMessage() {
        String message = getString(R.string.no_photos_found_label);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageSelected(Photo photo) {
        mImageSearchPresenter.onImageSetToFavourite(photo);
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        if (mImageRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mImageRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mImageSearchAdapter.setIsGridTypeSelected(true);
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                break;
            case LINEAR_LAYOUT_MANAGER:
                mImageSearchAdapter.setIsGridTypeSelected(false);
                mLayoutManager = new LinearLayoutManager(getActivity());
                break;
        }
        mImageRecyclerView.setLayoutManager(mLayoutManager);
        mImageRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
        mImageSearchPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
