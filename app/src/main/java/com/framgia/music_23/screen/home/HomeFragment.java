package com.framgia.music_23.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.music_23.R;
import com.framgia.music_23.screen.my_song.MySongActivity;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        TextView textLoveSong = view.findViewById(R.id.text_love_song);
        textLoveSong.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_love_song:
                startActivity(new Intent(getContext(), MySongActivity.class));
                break;
        }
    }
}
