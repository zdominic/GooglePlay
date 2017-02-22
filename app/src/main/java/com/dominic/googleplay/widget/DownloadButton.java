package com.dominic.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.bean.DownloadInfo;
import com.dominic.googleplay.network.DownloadManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Dominic on 2017/2/20.
 */

public class DownloadButton extends Button implements Observer{

    private int mProgress = 0;
    private Paint mPaint;
    private ColorDrawable mColorDrawable;

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    private int mMax = 100;

    public int getProgress() {
        return mProgress;
    }

    private boolean enableProgress = true;

    public void setProgress(int progress) {
        mProgress = progress;
        String progressString = String.format(getResources().getString(R.string.download_progress),
                (int) (mProgress * 1.0f / mMax * 100));

        setText(progressString);
    }

    public DownloadButton(Context context) {
        this(context, null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);  //设置抗锯齿
        mPaint.setColor(Color.BLUE);

        mColorDrawable = new ColorDrawable(Color.BLUE);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (enableProgress){

            int right = (int) (mProgress * 1.0f / mMax * getWidth());
            // canvas.drawRect(0,0,right,getBottom(),mPaint);
            mColorDrawable.setBounds(0, 0, right, getBottom());
            mColorDrawable.draw(canvas);
        }


        super.onDraw(canvas);
    }


    public void syncState(AppDetailBean appDetailBean) {
        DownloadInfo downloadInfo = DownloadManager.getInstance().initDownloadInfo(getContext(),
                appDetailBean.packageName, appDetailBean.size,appDetailBean.downloadUrl);
        DownloadManager.getInstance().addObserve(downloadInfo.getPackageName(),this);
        updateState(downloadInfo);

    }

    private void updateState(DownloadInfo downloadInfo) {
        switch (downloadInfo.getStatus()) {
            case DownloadManager.STATE_INSTALLED:
                setText(R.string.open);
                break;
            case DownloadManager.STATE_DOWNLOADED:
                setText(R.string.install);
                clearProgress();
                break;
            case DownloadManager.STATE_UN_DOWNLOAD:
                setText(R.string.download);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                enableProgress = true;
                setMax(downloadInfo.getSize());
                setProgress(downloadInfo.getProgress());
                break;
            case DownloadManager.STATE_WAITING:
                setText(R.string.waiting);
                break;
            case DownloadManager.STATE_FAILED:
                setText(R.string.retry);
                clearProgress();
                break;
            case DownloadManager.STATE_PAUSE:
                setText(R.string.continue_download);
                clearProgress();
                break;
        }
    }

    /**
     * 清除进度
     */
    private void clearProgress() {
        enableProgress = false;
        invalidate();  //重新绘制
    }

    /*---------------DownloadButton作为观察者接收到的状态改变去更新UI ---------------*/
    @Override
    public void update(Observable o, Object arg) {
        final DownloadInfo downloadInfo = (DownloadInfo) arg;

        post(new Runnable() {
            @Override
            public void run() {
                updateState(downloadInfo);
            }
        });
    }
}
