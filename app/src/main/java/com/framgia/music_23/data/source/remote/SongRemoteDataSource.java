package com.framgia.music_23.data.source.remote;

import com.framgia.music_23.data.model.Artist;
import com.framgia.music_23.data.model.MoreDataSong;
import com.framgia.music_23.data.model.Song;
import com.framgia.music_23.data.source.DataCallBack;
import com.framgia.music_23.data.source.OnFetchDataListener;
import com.framgia.music_23.data.source.SongDataSource;
import com.framgia.music_23.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongRemoteDataSource implements SongDataSource.RemoteDataSoure {

    private static SongRemoteDataSource sInstance;

    public static SongRemoteDataSource getInstance() {
        return (sInstance == null) ? sInstance = new SongRemoteDataSource() : sInstance;
    }

    private MoreDataSong parseJSON(String jsonString) throws JSONException {
        MoreDataSong moreDataSong = new MoreDataSong();
        List<Song> songList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.COLLECTION);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject songJsonObject = jsonArray.getJSONObject(i);
                JSONObject artistJsonObject = songJsonObject.getJSONObject(Constants.USER);

                Song song = new Song.Builder().withId(songJsonObject.getInt(Song.SongPropertyAPI.ID))
                        .withArtworkUrl(songJsonObject.getString(Song.SongPropertyAPI.ARTWORK_URL))
                        .withGenre(songJsonObject.getString(Song.SongPropertyAPI.GENRE))
                        .withStreamUrl(songJsonObject.getString(Song.SongPropertyAPI.STREAM_URL))
                        .withUri(songJsonObject.getString(Song.SongPropertyAPI.URI))
                        .withTitle(songJsonObject.getString(Song.SongPropertyAPI.TITLE))
                        .withDuration(songJsonObject.getInt(Song.SongPropertyAPI.DURATION))
                        .withArtist(new Artist(artistJsonObject.getString(Artist.ArtistPropertyAPI.AVATAR_URL),
                                artistJsonObject.getInt(Artist.ArtistPropertyAPI.ARTIST_ID),
                                artistJsonObject.getString(Artist.ArtistPropertyAPI.ARTIST_NAME)))
                        .build();
                songList.add(song);
            }
            String nextHref = jsonObject.getString(Constants.NEXT_HREF);
            moreDataSong.setSongs(songList);
            moreDataSong.setNextHref(nextHref);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moreDataSong;
    }

    @Override
    public void getSongsByGenre(String genre, final DataCallBack<MoreDataSong> callBack) {
        new GetDataFromAPI(new OnFetchDataListener() {
            @Override
            public void onSuccess(String data) {
                MoreDataSong moreData = null;
                try {
                    moreData = parseJSON(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callBack.onSuccess(moreData);
            }

            @Override
            public void onFail(Exception e) {
                callBack.onFail(e);
            }
        }).execute(Constants.GENRES_URL + genre);
    }
}
