package com.framgia.music_23.screen.splash;

import android.os.Handler;

import com.framgia.music_23.utils.Constants;

public class SplashPresenter implements SplashContract.Presenter {

    private static final int DELAY_TIME = 500;
    private SplashContract.View mView;
    public SplashPresenter(SplashContract.View view) {
        mView = view;
    }

    @Override
    public void runSplash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.changeView();
            }
        }, DELAY_TIME);
    }
}
