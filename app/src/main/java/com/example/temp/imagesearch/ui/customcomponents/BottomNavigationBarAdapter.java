package com.example.temp.imagesearch.ui.customcomponents;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 12/11/17.
 */

public class BottomNavigationBarAdapter extends CustomFragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    public BottomNavigationBarAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
}
