package com.framgia.music_23.screen.singer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_23.R;


public class SingerFragment extends Fragment {

    public static SingerFragment newInstance() {
        return new SingerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_singer, container, false);
    }

}
