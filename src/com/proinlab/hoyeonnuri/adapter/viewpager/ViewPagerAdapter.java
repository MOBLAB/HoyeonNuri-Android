package com.proinlab.hoyeonnuri.adapter.viewpager;

import android.util.Log;
import proinfactory.com.hoyeonnuri.*;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public static final int FRAGMENT_MAIN = 0;
    public static final int FRAGMENT_MAIN_DEVINFO = 1;

    public static final int FRAGMENT_BOARD_NOTICE = 0;
    public static final int FRAGMENT_BOARD_FREE = 1;
    public static final int FRAGMENT_BOARD_REPAIR = 2;
    public static final int FRAGMENT_BOARD_QNA = 3;
    public static final int FRAGMENT_BOARD_DEVINFO = 4;

    public static final int FRAGMENT_RESTAURANT_NOTICE = 0;
    public static final int FRAGMENT_RESTAURANT_JINLI = 1;
    public static final int FRAGMENT_RESTAURANT_HOYEON = 2;
    public static final int FRAGMENT_RESTAURANT_STUDENT = 3;
    public static final int FRAGMENT_RESTAURANT_DEVINFO = 4;

    public static final int FRAGMENT_INFO_SUTTLE = 0;
    public static final int FRAGMENT_INFO_LIBRARY_SEJONG = 1;
    public static final int FRAGMENT_INFO_LIBRARY_ANAM = 2;
    public static final int FRAGMENT_INFO_DEVINFO = 3;

    public static final int FRAGMENT_DELIVERY = 0;
    public static final int FRAGMENT_DELIVERY_DEVINFO = 2;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}