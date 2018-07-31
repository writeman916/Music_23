package com.framgia.music_23.screen.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.framgia.music_23.screen.main.MainActivity;
import com.framgia.music_23.R;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        mPresenter.runSplash();
    }

    private void initView() {
        mPresenter = new SplashPresenter(this);
    }

    @Override
    public void changeView() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
