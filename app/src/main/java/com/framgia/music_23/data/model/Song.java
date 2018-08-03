package com.framgia.music_23.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private int mId;
    private String mUri;
    private String mGenre;
    private String mTitle;
    private String mStreamUrl;
    private String mArtworkUrl;
    private int mDuration;
    private Artist mArtist;

    private Song(Parcel in) {
        mId = in.readInt();
        mUri = in.readString();
        mGenre = in.readString();
        mTitle = in.readString();
        mStreamUrl = in.readString();
        mArtworkUrl = in.readString();
        mDuration = in.readInt();
        mArtist = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mUri);
        parcel.writeString(mGenre);
        parcel.writeString(mTitle);
        parcel.writeString(mStreamUrl);
        parcel.writeString(mArtworkUrl);
        parcel.writeInt(mDuration);
        parcel.writeParcelable(mArtist, i);
    }

    protected Song(Builder builder) {
        mId = builder.mId;
        mUri = builder.mUri;
        mGenre = builder.mGenre;
        mTitle = builder.mTitle;
        mStreamUrl = builder.mStreamUrl;
        mArtworkUrl = builder.mArtworkUrl;
        mDuration = builder.mDuration;
        mArtist = builder.mArtist;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public static Creator<Song> getCREATOR() {
        return CREATOR;
    }

    public Artist getArtist() {
        return mArtist;
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    public static class Builder {
        private int mId;
        private String mUri;
        private String mGenre;
        private String mTitle;
        private String mStreamUrl;
        private String mArtworkUrl;
        private int mDuration;
        private Artist mArtist;

        public Builder() {
        }

        public Builder(int id, String uri, String genre, String title, String streamUrl, String artworkUrl,
                int duration, Artist artist) {
            mId = id;
            mUri = uri;
            mGenre = genre;
            mTitle = title;
            mStreamUrl = streamUrl;
            mArtworkUrl = artworkUrl;
            mDuration = duration;
            mArtist = artist;
        }

        public Builder withId(int id) {
            mId = id;
            return this;
        }
        public Builder withUri(String uri) {
            mUri = uri;
            return this;
        }

        public Builder withGenre(String genre) {
            mGenre = genre;
            return this;
        }

        public Builder withTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder withStreamUrl(String streamUrl) {
            mStreamUrl = streamUrl;
            return this;
        }

        public Builder withArtworkUrl(String artworkUrl) {
            mArtworkUrl = artworkUrl;
            return this;
        }

        public Builder withDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public Builder withArtist(Artist artist) {
            mArtist = artist;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }
    public class SongPropertyAPI {
        public static final String ID = "id";
        public static final String URI = "uri";
        public static final String TITLE = "title";
        public static final String STREAM_URL = "stream_url";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String GENRE = "genre";
        public static final String DURATION = "duration";
    }
}
