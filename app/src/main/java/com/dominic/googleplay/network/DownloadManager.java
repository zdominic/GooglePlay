package com.dominic.googleplay.network;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.DownloadInfo;
import com.dominic.googleplay.util.ThreadPoolProxy;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dominic on 2017/2/20.
 * 管理下载所有功能
 * 1.检查是否已经安装 -->已安装则打开
 * 2.检查是否已经下载 -->已经下载则安装应用
 */

public class DownloadManager {

    public static final int STATE_UN_DOWNLOAD = 0;//未下载
    public static final int STATE_DOWNLOADING = 1;//下载中
    public static final int STATE_PAUSE = 2;//暂停下载
    public static final int STATE_WAITING = 3;//等待下载
    public static final int STATE_FAILED = 4;//下载失败
    public static final int STATE_DOWNLOADED = 5;//下载完成
    public static final int STATE_INSTALLED = 6;//已安装


    private static final String DEFAULT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/com.dominic.googleplay/apk/";

    private static DownloadManager mDownloadManager;

    private OkHttpClient mOkHttpClient;

    //保存一个包名对应下载信息 key 包
    private HashMap<String, DownloadInfo> mDownloadInfoHashMap = new HashMap<String, DownloadInfo>();

    private Map<String, Observer> mObserverMap = new HashMap<String, Observer>();


    /*--------------- 添加观察者 ---------------*/
    public void addObserve(String packageName, Observer observer) {
        mObserverMap.put(packageName, observer);
    }

    /*--------------- 移除观察者 ---------------*/
    public void removeObserve(String packageName) {
        mObserverMap.remove(packageName);
    }

    /*--------------- 通知观察者消息改变 ---------------*/
    public void notifyObserver(String packageName) {
        Observer observer = mObserverMap.get(packageName);
        DownloadInfo downloadInfo = mDownloadInfoHashMap.get(packageName);
        if (observer != null) {
            observer.update(null, downloadInfo);
        }

    }


    private DownloadManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public static DownloadManager getInstance() {
        if (mDownloadManager == null) {
            synchronized (DownloadManager.class) {
                if (mDownloadManager == null) {
                    mDownloadManager = new DownloadManager();
                }
            }
        }
        return mDownloadManager;
    }

    public void creatDir() {
        File file = new File(DEFAULT_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    /*
   * 初始化对应包名的下载信息
   * */
    public DownloadInfo initDownloadInfo(Context context, String packageName, int size, String downloadUrl) {

        //如果已经初始化过了，就直接返回
        if (mDownloadInfoHashMap.get(packageName) != null) {
            return mDownloadInfoHashMap.get(packageName);
        }

        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setPackageName(packageName);
        downloadInfo.setSize(size);
        downloadInfo.setDownloadUrl(downloadUrl);

        //判断是否安装
        if (isInstalled(context, packageName)) {
            downloadInfo.setStatus(STATE_INSTALLED);
        } else if (isDownloaded(downloadInfo)) {//判断是否已经下载完成
            downloadInfo.setStatus(STATE_DOWNLOADED);
        } else {
            downloadInfo.setStatus(STATE_UN_DOWNLOAD);
        }
        //保存结果
        mDownloadInfoHashMap.put(packageName, downloadInfo);
        return downloadInfo;
    }


    /*--------------- 判断是否已经安装 ---------------*/
    public boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /*--------------- 判断应用是否下载 ---------------*/
    public boolean isDownloaded(DownloadInfo downloadInfo) {

        //是否在下载目录下存在apk文件并且大小等于服务器返回给我的大小数据
        String filePath = DEFAULT_DIR + downloadInfo.getPackageName() + ".apk";
        //保存下文件路径，安装要用到
        downloadInfo.setFilePath(filePath);
        File file = new File(filePath);
        if (file.exists()) {
            if (file.length() == downloadInfo.getSize()) {
                return true;
            } else {
                //如果已经下载了一部分，将下载的进度记录
                downloadInfo.setProgress((int) file.length());
            }
        }
        return false;
    }


    /*--------------- 开打app ---------------*/
    public void openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }


    /*--------------- 处理点击事件 ---------------*/
    public void handleDownloadAction(Context context, String packageName) {
        DownloadInfo downloadInfo = mDownloadInfoHashMap.get(packageName);
        switch (downloadInfo.getStatus()) {
            case STATE_INSTALLED:  //已经安装完成
                openApp(context, packageName);
                break;
            case STATE_DOWNLOADED:   //已经下载完成
                //安装应用
                installApp(context, downloadInfo.getFilePath());
                break;
            case STATE_UN_DOWNLOAD:   //没有下载
                //如果是未下载状态则去下载应用
                download(downloadInfo);
                break;
            case STATE_PAUSE:
                download(downloadInfo);
                break;
            case STATE_DOWNLOADING:
                pauseDownload(downloadInfo);
                break;
            case STATE_FAILED:
                download(downloadInfo);
                break;
            case STATE_WAITING:
                //取消任务
                cancel(downloadInfo);
                break;
        }

    }

    private void cancel(DownloadInfo downloadInfo) {
        ThreadPoolProxy.getInstance().remove(downloadInfo.getDownloadTask());
        downloadInfo.setStatus(STATE_UN_DOWNLOAD);
        notifyObserver(downloadInfo.getPackageName());

    }

    /*--------------- 安装APP ---------------*/
    private void installApp(Context context, String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /*--------------- 下载APP ---------------*/
    public void download(DownloadInfo downloadInfo) {
        //下载之前处于等待状态
        downloadInfo.setStatus(STATE_WAITING);
        notifyObserver(downloadInfo.getPackageName());

        DownloadTask task = new DownloadTask(downloadInfo);
        downloadInfo.setDownloadTask(task);
        ThreadPoolProxy.getInstance().execute(task);

  //      new Thread(new DownloadTask(downloadInfo)).start();

    }


    public class DownloadTask implements Runnable {

        private DownloadInfo mDownloadInfo;

        private DownloadTask(DownloadInfo downloadInfo) {
            mDownloadInfo = downloadInfo;
        }


        @Override
        public void run() {
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            String url = Constants.BASE_DOWNLOAD_URL + mDownloadInfo.getDownloadUrl() +
                    "&range=" + mDownloadInfo.getProgress();

            Request request = new Request.Builder().url(url).get().build();
            try {
                Response response = mOkHttpClient.newCall(request).execute();
                //将数据写入apk的目录
                if (response.isSuccessful()) {
                    inputStream = response.body().byteStream();
                    File file = new File(mDownloadInfo.getFilePath());
                    if (!file.exists()) {
                        creatDir();
                    }
                    fileOutputStream = new FileOutputStream(file, true);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        //如果是暂停状态,停止下载,返回
                        if (mDownloadInfo.getStatus() == STATE_PAUSE){
                            return;
                        }

                        fileOutputStream.write(buffer, 0, len);
                        mDownloadInfo.setStatus(STATE_DOWNLOADING);//正在下载的状态
                        //保存下载进度
                        int progress = (int) (mDownloadInfo.getProgress() + len);
                        mDownloadInfo.setProgress(progress);

                        //通知观察者更新进度
                        notifyObserver(mDownloadInfo.getPackageName());

                        //如果大小等于文件大小就返回了
                        if (mDownloadInfo.getProgress() == mDownloadInfo.getSize()){
                            break;
                        }

                    }
                    mDownloadInfo.setStatus(STATE_DOWNLOADED);
                    notifyObserver(mDownloadInfo.getPackageName());

                } else {
                    //更新状态,下载失败
                    mDownloadInfo.setStatus(STATE_FAILED);
                    notifyObserver(mDownloadInfo.getPackageName());
                }


            } catch (IOException e) {
                e.printStackTrace();
                mDownloadInfo.setStatus(STATE_FAILED);
                notifyObserver(mDownloadInfo.getPackageName());
            } finally {

                closeStream(inputStream);
                closeStream(fileOutputStream);

            }
        }

    }

    /*--------------- io流的关闭 ---------------*/
    private void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*--------------- 暂停下载 ---------------*/
    private void pauseDownload(DownloadInfo downloadInfo) {
        downloadInfo.setStatus(STATE_PAUSE);
        notifyObserver(downloadInfo.getPackageName());
    }


}
