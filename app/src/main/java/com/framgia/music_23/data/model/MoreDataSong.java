package com.framgia.music_23.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class MoreDataSong implements Parcelable {

    private List<Song> mSongs;
    private String mNextHref;

    public MoreDataSong() {
    }

    private MoreDataSong(Parcel in) {
        mSongs = in.createTypedArrayList(Song.CREATOR);
        mNextHref = in.readString();
    }

    public static final Creator<MoreDataSong> CREATOR = new Creator<MoreDataSong>() {
        @Override
        public MoreDataSong createFromParcel(Parcel in) {
            return new MoreDataSong(in);
        }

        @Override
        public MoreDataSong[] newArray(int size) {
            return new MoreDataSong[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mSongs);
        parcel.writeString(mNextHref);
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public void setSongs(List<Song> songs) {
        mSongs = songs;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    public static Creator<MoreDataSong> getCREATOR() {
        return CREATOR;
    }
}
