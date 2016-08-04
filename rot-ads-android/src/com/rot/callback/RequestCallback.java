package com.rot.callback;
public interface RequestCallback {
    void onResult(String content, Throwable error);
}
