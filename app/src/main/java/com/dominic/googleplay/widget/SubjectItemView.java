package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dominic.googleplay.R;
import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.SubjectBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/17.
 */

public class SubjectItemView extends RelativeLayout {

    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.title)
    TextView mTitle;

    public SubjectItemView(Context context) {
        this(context, null);
    }

    public SubjectItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_subject_view, this);
        ButterKnife.bind(this, this);

    }

    public void bindView(SubjectBean subjectBean) {
        mTitle.setText(subjectBean.des);
        String iconUrl = Constants.BASE_IMAGE_URL + subjectBean.url;
        Glide.with(getContext()).load(iconUrl).placeholder(R.drawable.ic_default).centerCrop().into(mIcon);
      //  Picasso.with(getContext()).load(iconUrl).placeholder(R.drawable.ic_default).into(mIcon);
    }
}
