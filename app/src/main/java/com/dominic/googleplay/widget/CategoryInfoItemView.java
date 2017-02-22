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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/16.
 */

public class CategoryInfoItemView extends RelativeLayout {


    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.title)
    TextView mTitle;

    public CategoryInfoItemView(Context context) {
        this(context, null);
    }

    public CategoryInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_info_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(String name, String url) {
        mTitle.setText(name);
        String iconUrl = Constants.BASE_IMAGE_URL + url;
        Glide.with(getContext()).load(iconUrl).into(mIcon);
    }
}
