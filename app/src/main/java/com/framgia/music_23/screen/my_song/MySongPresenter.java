package com.framgia.music_23.screen.my_song;

import com.framgia.music_23.data.model.MoreDataSong;
import com.framgia.music_23.data.repository.SongRepository;
import com.framgia.music_23.data.source.DataCallBack;

public class MySongPresenter implements MySongContract.Presenter {
    private MySongContract.View mView;
    private SongRepository mSongRepository;

    MySongPresenter(MySongContract.View view, SongRepository songRepository) {
        mView = view;
        mSongRepository = songRepository;
    }

    @Override
    public void getSongsByGenre(String genre) {
        mSongRepository.getSongsByGenre(genre, new DataCallBack<MoreDataSong>() {
            @Override
            public void onSuccess(MoreDataSong data) {
                mView.onGetSongSuccess(data.getSongs());
            }
            @Override
            public void onFail(Exception e) {
                mView.OnError(e);
            }
        });
    }
}
