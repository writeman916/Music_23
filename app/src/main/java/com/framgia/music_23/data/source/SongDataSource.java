package com.framgia.music_23.data.source;

import com.framgia.music_23.data.model.MoreDataSong;

public interface SongDataSource {
    interface RemoteDataSoure{
        void getSongsByGenre(String genre, final DataCallBack<MoreDataSong> callBack);
    }
}
