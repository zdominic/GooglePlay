package com.dominic.googleplay.bean;

import com.dominic.googleplay.network.DownloadManager;

/**
 * Created by Dominic on 2017/2/20.
 */

public class DownloadInfo {

    private String packageName;
    private String downloadUrl;
    private String filePath;
    private int status = DownloadManager.STATE_UN_DOWNLOAD;

    public void setDownloadTask(DownloadManager.DownloadTask downloadTask) {
        this.downloadTask = downloadTask;
    }

    public DownloadManager.DownloadTask getDownloadTask() {
        return downloadTask;
    }

    private DownloadManager.DownloadTask downloadTask;
    private int size;
    private int progress;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
