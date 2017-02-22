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
import com.dominic.googleplay.bean.AppListItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppListItemView extends LinearLayout {

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.stars)
    RatingBar mStars;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.circle_download)
    CircleDownloadView mCircleDownload;
    @BindView(R.id.desc)
    TextView mDesc;
    private AppListItemBean mAppListItemBean;

    public AppListItemView(Context context) {
        this(context, null);
    }

    public AppListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_list_item, this);
        ButterKnife.bind(this, this);


    }

    public void bindView(AppListItemBean appListItemBean) {
        mAppListItemBean = appListItemBean;
        String iconUrl = Constants.BASE_IMAGE_URL + appListItemBean.iconUrl;
        Glide.with(getContext()).load(iconUrl).into(mIcon);
        mName.setText(appListItemBean.name);
        mStars.setRating((float) appListItemBean.stars);
        mSize.setText(Formatter.formatFileSize(getContext(), appListItemBean.size));
        mDesc.setText(appListItemBean.des);

        mCircleDownload.syncState(mAppListItemBean);
    }


}
