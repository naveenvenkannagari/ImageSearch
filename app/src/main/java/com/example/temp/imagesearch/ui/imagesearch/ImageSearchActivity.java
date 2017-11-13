package com.example.temp.imagesearch.ui.imagesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.ui.customcomponents.BottomNavigationBarAdapter;
import com.example.temp.imagesearch.ui.customcomponents.BottomNavigationPager;
import com.example.temp.imagesearch.ui.imagefavourite.ImageFavouriteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by temp on 11/11/17.
 */

public class ImageSearchActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.no_swipe_view_pager)
    BottomNavigationPager bottomNavigationPager;

    private Unbinder mUnbinder;
    private BottomNavigationBarAdapter mBottomNavigationBarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        initUI();
    }

    private void initUI() {
        mUnbinder = ButterKnife.bind(this);
        bottomNavigationPager.setPagingEnabled(false);
        mBottomNavigationBarAdapter = new BottomNavigationBarAdapter(getSupportFragmentManager());
        Fragment imageFavouriteFragment = new ImageFavouriteFragment();
        Fragment imageSearchFragment = new ImageSearchFragment();
        mBottomNavigationBarAdapter.addFragments(imageSearchFragment);
        mBottomNavigationBarAdapter.addFragments(imageFavouriteFragment);
        bottomNavigationPager.setAdapter(mBottomNavigationBarAdapter);
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            bottomNavigationPager.setCurrentItem(item.getItemId() == R.id.action_search ? 0 : 1);
            return false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // clearing the fragments which are associated with the view pager
        if (mBottomNavigationBarAdapter != null) {
            mBottomNavigationBarAdapter.getFragments().clear();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
