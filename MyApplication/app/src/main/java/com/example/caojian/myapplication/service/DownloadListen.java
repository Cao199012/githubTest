package com.example.caojian.myapplication.service;

/**
 * Created by caojian on 2017/3/2.
 */
public interface DownloadListen {
    void onProgress(int progress);

    void onSuccess();

    void onDailed();

    void onPused();

    void onCancle();
}
