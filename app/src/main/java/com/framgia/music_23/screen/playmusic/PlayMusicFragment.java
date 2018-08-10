package com.framgia.music_23.screen.playmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_23.R;
import com.framgia.music_23.data.model.Song;
import com.framgia.music_23.screen.service.MusicService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends Fragment
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final String ARGUMENT_POSITISON = "ARGUMENT_POSITISON";
    private static final String FORMAT_TIME = "mm:ss";
    private static final int BASE_DIV = 1000;
    private ImageButton mButtonPlay;
    private TextView mTextNameSong, mTextNameArtist, mTextCurrentTime, mTextEndTime;
    private SeekBar mSeekBar;
    private CircleImageView mCircleImageViewDisk;
    private MusicService mMusicService;
    private boolean mIsBound;
    private Handler mHandler;
    private SimpleDateFormat mSimpleDateFormat;

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
        updateProgressBar();
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
            case R.id.button_next:
                mMusicService.nextMusic();
                setUpViews();
                break;
            case R.id.button_previous:
                mMusicService.previousMusic();
                setUpViews();
                break;
        }
    }

    void initView(View view) {
        mHandler = new Handler();
        mSimpleDateFormat = new SimpleDateFormat(FORMAT_TIME);

        ImageButton buttonNext = view.findViewById(R.id.button_next);
        ImageButton buttonPrevious = view.findViewById(R.id.button_previous);
        mButtonPlay = view.findViewById(R.id.button_play);
        mTextNameSong = view.findViewById(R.id.text_title_song);
        mTextNameArtist = view.findViewById(R.id.text_name_artist);
        mCircleImageViewDisk = view.findViewById(R.id.image_disk);
        mTextCurrentTime = view.findViewById(R.id.text_curent_time);
        mTextEndTime = view.findViewById(R.id.text_end_time);
        mSeekBar = view.findViewById(R.id.seekbar);

        mButtonPlay.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_pause));
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

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            mSeekBar.setMax((int) mMusicService.getSongDuration() / BASE_DIV);
            mSeekBar.setProgress(mMusicService.getCurrentPosition() / BASE_DIV);
            mTextCurrentTime.setText(mSimpleDateFormat.format(mMusicService.getCurrentPosition()));
            mTextEndTime.setText(mSimpleDateFormat.format(mMusicService.getSongDuration()));
            String currentPosition = mSimpleDateFormat.format(mMusicService.getCurrentPosition());
            String totalTime = mSimpleDateFormat.format(mMusicService.getSongDuration());
            if (currentPosition.equals(totalTime) && mMusicService.getCurrentPosition() != 0) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                mButtonPlay.setImageDrawable(getResources().getDrawable(R.drawable.icon_play));
                mMusicService.nextMusic();
                setUpViews();
            }

            mHandler.postDelayed(this, BASE_DIV);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mMusicService != null && fromUser) {
            mMusicService.seekTo(progress * BASE_DIV);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateProgressBar();
    }

    public void updateProgressBar() {
        mHandler.post(mUpdateTimeTask);
    }
}
