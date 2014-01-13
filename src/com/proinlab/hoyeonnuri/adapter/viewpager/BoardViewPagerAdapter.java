package com.proinlab.hoyeonnuri.adapter.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import proinfactory.com.hoyeonnuri.fragment.FragmentBoard;
import proinfactory.com.hoyeonnuri.fragment.FragmentDevInfo;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class BoardViewPagerAdapter extends ViewPagerAdapter {

    private String[] catelist = {"공지사항","자유게시판","수리해주세요","Q&A","개발자 정보"};

    public BoardViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("TAG", "POSITION = " + Integer.toString(position));
        Fragment fragment = null;
        Bundle args = null;
        switch (position) {
            case FRAGMENT_BOARD_NOTICE:
                fragment = new FragmentBoard();
                args = new Bundle();
                args.putInt(FragmentBoard.ARG_URL, FRAGMENT_BOARD_NOTICE);
                fragment.setArguments(args);
                break;
            case FRAGMENT_BOARD_FREE:
                fragment = new FragmentBoard();
                args = new Bundle();
                args.putInt(FragmentBoard.ARG_URL, FRAGMENT_BOARD_FREE);
                fragment.setArguments(args);
                break;
            case FRAGMENT_BOARD_REPAIR:
                fragment = new FragmentBoard();
                args = new Bundle();
                args.putInt(FragmentBoard.ARG_URL, FRAGMENT_BOARD_REPAIR);
                fragment.setArguments(args);
                break;
            case FRAGMENT_BOARD_QNA:
                fragment = new FragmentBoard();
                args = new Bundle();
                args.putInt(FragmentBoard.ARG_URL, FRAGMENT_BOARD_QNA);
                fragment.setArguments(args);
                break;
            case FRAGMENT_BOARD_DEVINFO:
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
