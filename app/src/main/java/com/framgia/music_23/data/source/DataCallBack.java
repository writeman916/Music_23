package com.framgia.music_23.data.source;

public interface DataCallBack<T> {
    void onSuccess(T data);

    void onFail(Exception e);
}
