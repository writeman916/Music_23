package com.framgia.music_23.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.music_23.screen.genres.GenresFragment;
import com.framgia.music_23.screen.home.HomeFragment;
import com.framgia.music_23.screen.singer.SingerFragment;
import com.framgia.music_23.utils.Tab;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();

    MainAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Tab.TAB_HOME:
                return HomeFragment.newInstance();
            case Tab.TAB_SINGER:
                return SingerFragment.newInstance();
            case Tab.TAB_GENRES:
                return GenresFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            mFragments.add(fragment);
        }
    }
}
