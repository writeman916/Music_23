package com.framgia.music_23.data.repository;

import com.framgia.music_23.data.model.MoreDataSong;
import com.framgia.music_23.data.source.DataCallBack;
import com.framgia.music_23.data.source.SongDataSource;
import com.framgia.music_23.data.source.remote.SongRemoteDataSource;

public class SongRepository implements SongDataSource.RemoteDataSoure {

    private static SongRepository sInstance;
    private SongRemoteDataSource mSongRemoteDataSource;

    private SongRepository(SongRemoteDataSource songRemoteDataSource) {
        mSongRemoteDataSource = songRemoteDataSource;
    }

    public static synchronized SongRepository getInstance(
            SongRemoteDataSource songRemoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(songRemoteDataSource);
        }
        return sInstance;
    }

    @Override
    public void getSongsByGenre(String genre, DataCallBack<MoreDataSong> callBack) {
        if (mSongRemoteDataSource != null) mSongRemoteDataSource.getSongsByGenre(genre, callBack);
    }
}
