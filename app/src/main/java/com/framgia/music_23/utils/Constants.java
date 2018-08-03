package com.framgia.music_23.utils;

import com.framgia.music_23.BuildConfig;

public class Constants {

    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static final String REQUEST_METHOD = "GET";
    public static final String API_KEY = BuildConfig.API_KEY+"&limit=10";
    public static final String BASE_URL = "http://api.soundcloud.com/tracks";
    public static final String CLIENT_ID = "?client_id=" + API_KEY;
    public static final String COLLECTION = "collection";
    public static final String USER = "user";
    public static final String NEXT_HREF = "next_href";
    public static final String GENRES_URL = BASE_URL + CLIENT_ID + "&linked_partitioning=1&genres=";

    private Constants() {
    }
}
