package com.framgia.music_23.screen.my_song;

import com.framgia.music_23.data.model.Song;
import java.util.List;

public interface MySongContract {
    interface View {
        void onGetSongSuccess(List<Song> songs);

        void OnError(Exception e);
    }

    interface Presenter {
        void getSongsByGenre(String genre);
    }
}
