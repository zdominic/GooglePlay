package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dominic.googleplay.R;
import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppDetailSecurityView extends LinearLayout {

    @BindView(R.id.tags_container)
    LinearLayout mTagsContainer;
    @BindView(R.id.des_container)
    LinearLayout mDesContainer;
    @BindView(R.id.arrow)
    ImageView mArrow;

    private boolean isOpen = false;


    public AppDetailSecurityView(Context context) {
        this(context, null);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_security, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean appDetailBean) {

        for (int i = 0; i < appDetailBean.safe.size(); i++) {
            AppDetailBean.SafeBean safeBean = appDetailBean.safe.get(i);
            String iconUrl = Constants.BASE_IMAGE_URL + safeBean.safeUrl;
            ImageView tag = new ImageView(getContext());
            Glide.with(getContext()).load(iconUrl).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(tag);
            mTagsContainer.addView(tag);

            LinearLayout linearLayout = new LinearLayout(getContext());
            ImageView descIcon = new ImageView(getContext());
            String descIconUrl = Constants.BASE_IMAGE_URL + safeBean.safeDesUrl;
            Glide.with(getContext()).load(descIconUrl).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(descIcon);

            linearLayout.addView(descIcon);

            TextView textView = new TextView(getContext());
            textView.setText(safeBean.safeDes);
            linearLayout.addView(textView);

            mDesContainer.addView(linearLayout);

        }

    }

    @OnClick(R.id.arrow)
    public void onClick() {
        if (isOpen) {
            //关闭
            int measuredHeight = mDesContainer.getMeasuredHeight();
            UIUtils.getAnimatViewHeigh(mDesContainer, measuredHeight, 0);

            UIUtils.rotationView(mArrow, -180f, 0);

        } else {
            //打开
            mDesContainer.measure(0, 0); // 没有期望的测量
            //获得测量后的结果
            int measuredHeight = mDesContainer.getMeasuredHeight();
            UIUtils.getAnimatViewHeigh(mDesContainer, 0, measuredHeight);

            UIUtils.rotationView(mArrow, 0, -180f);

        }

        isOpen = !isOpen;
    }
}
