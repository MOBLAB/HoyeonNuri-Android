package com.proinlab.hoyeonnuri.adapter.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import proinfactory.com.hoyeonnuri.fragment.*;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class InfoViewPagerAdapter extends ViewPagerAdapter {

    private String[] catelist = {"셔틀버스","세종캠퍼스 도서관 좌석","안암캠퍼스 도서관 좌석"};

    public InfoViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("TAG", "POSITION = " + Integer.toString(position));
        Fragment fragment = null;
        Bundle args = null;
        switch (position) {
            case FRAGMENT_INFO_SUTTLE:
                fragment = new FragmentInfoBus();
                break;
            case FRAGMENT_INFO_LIBRARY_SEJONG:
                fragment = new FragmentLibrarySejong();
                break;
            case FRAGMENT_INFO_LIBRARY_ANAM:
                fragment = new FragmentLibraryAnam();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < catelist.length)
            return catelist[position];
        else
            return "";
    }

    @Override
    public int getCount() {
        return 3;
    }
}
