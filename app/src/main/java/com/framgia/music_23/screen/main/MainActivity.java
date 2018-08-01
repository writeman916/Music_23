package com.framgia.music_23.screen.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.framgia.music_23.R;
import com.framgia.music_23.screen.genres.GenresFragment;
import com.framgia.music_23.screen.home.HomeFragment;
import com.framgia.music_23.screen.singer.SingerFragment;
import com.framgia.music_23.utils.Tab;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private BottomNavigationView mMainNav;
    private ViewPager mViewPager;
    private MenuItem mPrevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mMainNav.setOnNavigationItemSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mainAdapter.addFragment(HomeFragment.newInstance());
        mainAdapter.addFragment(SingerFragment.newInstance());
        mainAdapter.addFragment(GenresFragment.newInstance());
        viewPager.setAdapter(mainAdapter);
    }

    void initView() {
        mMainNav = findViewById(R.id.main_nav);
        mViewPager = findViewById(R.id.main_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                mViewPager.setCurrentItem(Tab.TAB_HOME);
                return true;
            case R.id.nav_singer:
                mViewPager.setCurrentItem(Tab.TAB_SINGER);
                return true;
            case R.id.nav_genres:
                mViewPager.setCurrentItem(Tab.TAB_GENRES);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mPrevMenuItem != null) {
            mPrevMenuItem.setChecked(false);
        } else {
            mMainNav.getMenu().getItem(0).setChecked(false);
        }
        mMainNav.getMenu().getItem(position).setChecked(true);
        mPrevMenuItem = mMainNav.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
