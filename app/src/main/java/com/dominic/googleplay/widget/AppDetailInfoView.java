package com.dominic.googleplay.widget;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dominic.googleplay.R;
import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.AppDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppDetailInfoView extends LinearLayout {

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.app_rating_bar)
    RatingBar mAppRatingBar;
    @BindView(R.id.download_count)
    TextView mDownloadCount;
    @BindView(R.id.app_timestamp)
    TextView mAppTimestamp;
    @BindView(R.id.version_code)
    TextView mVersionCode;
    @BindView(R.id.app_size)
    TextView mAppSize;

    public AppDetailInfoView(Context context) {
        this(context, null);
    }

    public AppDetailInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_info, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean appDetailBean) {
        String iconUrl = Constants.BASE_IMAGE_URL + appDetailBean.iconUrl;
        Glide.with(getContext()).load(iconUrl).into(mIcon);

        mAppName.setText(appDetailBean.name);

        mAppRatingBar.setRating((float) appDetailBean.stars);

        String downloaNum = String.format(getResources().getString(R.string.download_count),appDetailBean.downloadNum);
        mDownloadCount.setText(downloaNum);

        String timestamp = String.format(getResources().getString(R.string.time),appDetailBean.date);
        mAppTimestamp.setText(timestamp);

        String version = String.format(getResources().getString(R.string.version_code),appDetailBean.version);
        mVersionCode.setText(version);

        String size = String.format(getResources().getString(R.string.app_size), Formatter.formatFileSize(getContext(),appDetailBean.size));
        mAppSize.setText(size);


    }
}
