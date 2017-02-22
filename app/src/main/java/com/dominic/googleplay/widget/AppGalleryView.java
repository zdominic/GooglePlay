package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dominic.googleplay.R;
import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.AppDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/19.
 */

public class AppGalleryView extends RelativeLayout {

    @BindView(R.id.gallery_container)
    LinearLayout mGalleryContainer;

    public AppGalleryView(Context context) {
        this(context, null);
    }

    public AppGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_gallery, this);
        ButterKnife.bind(this, this);
    }


    public void bindView(AppDetailBean appDetailBean) {

        for (int i = 0; i < appDetailBean.screen.size(); i++) {
            String url = Constants.BASE_IMAGE_URL + appDetailBean.screen.get(i);
            ImageView imageView = new ImageView(getContext());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            if (i != 0) {
                layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.padding);

            }
            imageView.setLayoutParams(layoutParams);
            mGalleryContainer.addView(imageView);
            Glide.with(getContext()).load(url).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(imageView);
          //  Glide.with(getContext()).load(url).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(imageView);


        }

    }
}
