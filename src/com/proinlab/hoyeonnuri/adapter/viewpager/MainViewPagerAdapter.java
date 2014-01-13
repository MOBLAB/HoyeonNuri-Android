package com.proinlab.hoyeonnuri.adapter.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import proinfactory.com.hoyeonnuri.fragment.FragmentDevInfo;
import proinfactory.com.hoyeonnuri.fragment.FragmentMain;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class MainViewPagerAdapter extends ViewPagerAdapter {
    private FragmentManager fm;
    public MainViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
        this.fm = fm;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = null;
        switch (position) {
            case FRAGMENT_MAIN:
                fragment = new FragmentMain();
                break;
            case FRAGMENT_MAIN_DEVINFO:
                fragment = new FragmentDevInfo();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
