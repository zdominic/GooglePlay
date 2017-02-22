package com.dominic.googleplay.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.AppListItemBean;
import com.dominic.googleplay.bean.DownloadInfo;
import com.dominic.googleplay.network.DownloadManager;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * Created by Dominic on 2017/2/18.
 */

public class CircleDownloadView extends LinearLayout implements Observer {


    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.download_status)
    TextView mDownloadStatus;
    private DownloadInfo mDownloadInfo;
    private RectF mRectF;
    private Paint mPaint;

    private boolean enableDraw = true;

    public CircleDownloadView(Context context) {
        this(context, null);
    }

    public CircleDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.circle_download_view, this);
        ButterKnife.bind(this, this);

        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);

        //viewgroup一般是不会绘制自己
        //如果想要viewgroup可以调用到onDraw, 第一可以设置背景， 第二打开绘制开关
        setWillNotDraw(false);


    }

    //  点击时候后改变状态
    @OnClick(R.id.icon)
    public void onClick() {
        DownloadManager.getInstance().handleDownloadAction(getContext(), mDownloadInfo.getPackageName());
    }


    public void syncState(AppListItemBean appListItemBean) {
        //由于listView的回收机制,如果mDownloadInfo不等于空,说明是原来绑定视图信息
        if (mDownloadInfo != null) {
            DownloadManager.getInstance().removeObserve(mDownloadInfo.getPackageName());
        }


        //初始化app信息
        mDownloadInfo = DownloadManager.getInstance().initDownloadInfo(getContext(),
                appListItemBean.packageName, appListItemBean.size, appListItemBean.downloadUrl);
        //添加观察者
        DownloadManager.getInstance().addObserve(mDownloadInfo.getPackageName(), this);
        updateState(mDownloadInfo);

    }

    private void updateState(DownloadInfo downloadInfo) {
        //由于update时候还有一些老app更新的runnable没有执行，过滤掉以前app残余更新的runnable
        if (downloadInfo.getPackageName().equals(mDownloadInfo.getPackageName())) {
            mDownloadInfo = downloadInfo;
            switch (downloadInfo.getStatus()) {
                case DownloadManager.STATE_INSTALLED:
                    mDownloadStatus.setText(R.string.open);
                    mIcon.setImageResource(R.drawable.ic_install);
                    break;
                case DownloadManager.STATE_DOWNLOADED:
                    clearProgress();
                    mDownloadStatus.setText(R.string.install);
                    mIcon.setImageResource(R.drawable.ic_install);
                    break;
                case DownloadManager.STATE_UN_DOWNLOAD:
                    mDownloadStatus.setText(R.string.download);
                    mIcon.setImageResource(R.drawable.ic_download);
                    break;
                case DownloadManager.STATE_DOWNLOADING:
                    enableDraw = true;
                    mIcon.setImageResource(R.drawable.ic_pause);
                    int progress = (int) (mDownloadInfo.getProgress() * 1.0f / mDownloadInfo.getSize() * 100);
                    String progressString = String.format(getResources().getString(R.string.download_progress), progress);
                    mDownloadStatus.setText(progressString);
                    break;
                case DownloadManager.STATE_WAITING:
                    mDownloadStatus.setText(R.string.waiting);
                    mIcon.setImageResource(R.drawable.ic_cancel);
                    break;
                case DownloadManager.STATE_FAILED:
                    mDownloadStatus.setText(R.string.retry);
                    mIcon.setImageResource(R.drawable.ic_redownload);
                    break;
                case DownloadManager.STATE_PAUSE:
                    mDownloadStatus.setText(R.string.continue_download);
                    mIcon.setImageResource(R.drawable.ic_download);
                    break;
            }
        }
    }

    private void clearProgress() {
        enableDraw = false;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (enableDraw) {

            int left = mIcon.getLeft() - 6;
            int top = mIcon.getTop() - 6;
            int right = mIcon.getRight() + 6;
            int bottom = mIcon.getBottom() + 6;
            Log.d(TAG, "onSizeChanged: " + left + " " + top + " " + right + " " + bottom + " ");
            mRectF.set(left, top, right, bottom);
            int startAngle = -90;
            int sweepAngle = (int) (mDownloadInfo.getProgress() * 1.0f / mDownloadInfo.getSize() * 360);
            canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);
        }


    }

    /*---------------CircleDownloadView作为观察者接收到的状态改变去更新UI ---------------*/
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
