package com.framgia.music_23.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private String mAvatarUrl;
    private int mId;
    private String mUsername;

    public Artist(String avatarUrl, int id, String username) {
        mAvatarUrl = avatarUrl;
        mId = id;
        mUsername = username;
    }

    private Artist(Parcel in) {
        mAvatarUrl = in.readString();
        mId = in.readInt();
        mUsername = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mAvatarUrl);
        parcel.writeInt(mId);
        parcel.writeString(mUsername);
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public static Creator<Artist> getCREATOR() {
        return CREATOR;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public class ArtistPropertyAPI {
        public static final String ARTIST_ID = "id";
        public static final String ARTIST_NAME = "username";
        public static final String AVATAR_URL = "avatar_url";
    }
}
