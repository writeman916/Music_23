package com.framgia.music_23.screen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.framgia.music_23.data.model.Song;
import com.framgia.music_23.utils.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    private static final String EXTRA_SONG = "EXTRA_SONG";
    private static final String ARGUMENT_POSITISON = "ARGUMENT_POSITISON";
    private static final String MEDIA_ERROR_NOT_VALID =
            "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK ";
    private static final String MEDIA_ERROR_SERVER_DIED = "MEDIA ERROR SERVER DIED ";
    private static final String MEDIA_ERROR_UNKNOWN = "MEDIA ERROR UNKNOWN ";
    private IBinder mIBinder = new MusicBinder();
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs;
    private int mSongPosition;

    public static Intent newInstance(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putParcelableArrayListExtra(EXTRA_SONG, (ArrayList<? extends Parcelable>) songs);
        intent.putExtra(ARGUMENT_POSITISON, position);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mSongs = intent.getParcelableArrayListExtra(EXTRA_SONG);
        mSongPosition = intent.getIntExtra(ARGUMENT_POSITISON, 0);
        if (mSongs != null) {
            initMediaPlayer();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        playMedia();
    }

    public void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(
                    mSongs.get(mSongPosition).getStreamUrl() + Constants.CLIENT_ID);
        } catch (IOException e) {
            stopSelf();
        }
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopMedia();
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int extra) {
        switch (i) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, MEDIA_ERROR_NOT_VALID + extra, Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, MEDIA_ERROR_SERVER_DIED + extra, Toast.LENGTH_SHORT).show();
                break;

            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, MEDIA_ERROR_UNKNOWN + extra, Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    public String getArtistName() {
        return mSongs.get(mSongPosition) != null ? mSongs.get(mSongPosition)
                .getArtist()
                .getUsername() : "";
    }

    public String getSongName() {
        return mSongs.get(mSongPosition) != null ? mSongs.get(mSongPosition).getTitle() : "";
    }

    public String getArtwork() {
        return mSongs.get(mSongPosition) != null ? mSongs.get(mSongPosition).getArtworkUrl() : "";
    }

    public void playMedia() {
        if (!mMediaPlayer.isPlaying()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mMediaPlayer.start();
                }
            });
            thread.start();
        }
    }

    private void stopMedia() {
        if (mMediaPlayer == null) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void pauseMedia() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mSongPosition = mMediaPlayer.getCurrentPosition();
        }
    }

    public int getSongDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void seekTo(int position) {
        mMediaPlayer.seekTo(position);
    }

    private void resumeMedia() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(mSongPosition);
            mMediaPlayer.start();
        }
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void nextMusic() {
        if (mSongPosition == mSongs.size() - 1) {
            mSongPosition = 0;
        } else {
            mSongPosition++;
        }
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public void previousMusic() {
        if (mSongPosition == 0) {
            mSongPosition = mSongs.size() - 1;
        } else {
            mSongPosition--;
        }
        mMediaPlayer.reset();
        initMediaPlayer();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
