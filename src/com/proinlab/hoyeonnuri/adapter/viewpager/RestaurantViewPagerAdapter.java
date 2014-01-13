package com.proinlab.hoyeonnuri.adapter.viewpager;

import android.util.Log;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import proinfactory.com.hoyeonnuri.fragment.FragmentDevInfo;
import proinfactory.com.hoyeonnuri.fragment.FragmentRestaurant;
import proinfactory.com.hoyeonnuri.fragment.FragmentRestaurantBoard;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class RestaurantViewPagerAdapter extends ViewPagerAdapter {

    private String[] catelist = {"공지사항","진리관","호연4관","학생회관","개발자 정보"};

    public RestaurantViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle args = null;
        Log.i("TAG", "POSITION = " + Integer.toString(position));
        switch (position) {
            case FRAGMENT_RESTAURANT_NOTICE:
                fragment = new FragmentRestaurantBoard();
                args = new Bundle();
                args.putInt(FragmentRestaurantBoard.ARG_URL, FRAGMENT_RESTAURANT_NOTICE);
                fragment.setArguments(args);
                break;
            case FRAGMENT_RESTAURANT_JINLI:
                fragment = new FragmentRestaurant();
                args = new Bundle();
                args.putInt(FragmentRestaurant.ARG_URL, FRAGMENT_RESTAURANT_JINLI);
                fragment.setArguments(args);
                break;
            case FRAGMENT_RESTAURANT_HOYEON:
                fragment = new FragmentRestaurant();
                args = new Bundle();
                args.putInt(FragmentRestaurant.ARG_URL, FRAGMENT_RESTAURANT_HOYEON);
                fragment.setArguments(args);
                break;
            case FRAGMENT_RESTAURANT_STUDENT:
                fragment = new FragmentRestaurant();
                args = new Bundle();
                args.putInt(FragmentRestaurant.ARG_URL, FRAGMENT_RESTAURANT_STUDENT);
                fragment.setArguments(args);
                break;
            case FRAGMENT_RESTAURANT_DEVINFO:
                fragment = new FragmentDevInfo();
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
        return 5;
    }
}
