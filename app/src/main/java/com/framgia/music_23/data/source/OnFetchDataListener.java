package com.framgia.music_23.data.source;

public interface OnFetchDataListener {
    void onSuccess(String data);

    void onFail(Exception e);
}
