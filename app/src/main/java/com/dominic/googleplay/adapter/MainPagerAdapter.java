package com.dominic.googleplay.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dominic.googleplay.factory.FragmentFactoty;

/**
 * Created by Dominic on 2017/2/15.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private String[] mTitles;

    public MainPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return FragmentFactoty.getInstance().getFragment(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
