package com.rot.callback;
import java.io.File;

public interface MultiDownloadCallback {
    void onResult(File[] file, boolean success);
}
