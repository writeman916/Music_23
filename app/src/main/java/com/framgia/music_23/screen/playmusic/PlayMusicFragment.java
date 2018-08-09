package com.framgia.music_23.screen.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_23.R;
import com.framgia.music_23.data.model.Song;
import com.framgia.music_23.screen.service.MusicService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends Fragment implements View.OnClickListener {

    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final String ARGUMENT_POSITISON = "ARGUMENT_POSITISON";
    private ImageButton mButtonPlay;
    private TextView mTextNameSong, mTextNameArtist;
    private CircleImageView mCircleImageViewDisk;
    private MusicService mMusicService;
    private boolean mIsBound;

    public static PlayMusicFragment newInstance(List<Song> songs, int position) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_SONGS, (ArrayList<? extends Parcelable>) songs);
        args.putInt(ARGUMENT_POSITISON, position);
        fragment.setArguments(args);
        return fragment;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            setUpViews();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    private void setUpViews() {
        mTextNameSong.setText(mMusicService.getSongName());
        mTextNameArtist.setText(mMusicService.getArtistName());
        loadImageSong();
    }

    private void loadImageSong() {
        Glide.with(getActivity()).load(mMusicService.getArtwork()).into(mCircleImageViewDisk);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        getData();
        initView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_play:
                checkEventPlayMusic();
                break;
        }
    }

    void initView(View view) {
        mButtonPlay = view.findViewById(R.id.button_play);
        mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause));
        mButtonPlay.setOnClickListener(this);
        mTextNameSong = view.findViewById(R.id.text_title_song);
        mTextNameArtist = view.findViewById(R.id.text_name_artist);
        mCircleImageViewDisk = view.findViewById(R.id.image_disk);
    }

    public void getData() {
        assert getArguments() != null;
        List<Song> songs = getArguments().getParcelableArrayList(ARGUMENT_SONGS);
        int position = getArguments().getInt(ARGUMENT_POSITISON);
        Intent intent = MusicService.newInstance(getActivity(), songs, position);
        getActivity().startService(intent);
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void checkEventPlayMusic() {
        if (mMusicService.isPlaying()) {
            mMusicService.pauseMedia();
            mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play));
        } else {
            mMusicService.playMedia();
            mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause));
        }
    }
}
