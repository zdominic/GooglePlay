package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dominic on 2017/2/19.
 */

public class AppDetailDescriptionView extends RelativeLayout {

    private final int DEFAULT_LINE_COUNT = 7;

    private boolean isOpen = false;

    @BindView(R.id.app_des)
    TextView mAppDes;
    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.arrow)
    ImageView mArrow;
    private int mMeasuredHeight;

    public AppDetailDescriptionView(Context context) {
        this(context, null);
    }

    public AppDetailDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_desc, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean appDetailBean) {
        mAppDes.setText(appDetailBean.des);
        mAppName.setText(appDetailBean.name);

        /*--------------- 对全局布局完成的一个监听 ---------------*/
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                getViewTreeObserver().removeGlobalOnLayoutListener(this); // 只监听一次

                int lineCount = mAppDes.getLineCount();

                mMeasuredHeight = mAppDes.getMeasuredHeight();  //在设置行为7之前测量原始的高度

                if (lineCount > DEFAULT_LINE_COUNT){
                    mAppDes.setLines(DEFAULT_LINE_COUNT);
                }

            }
        });

    }

    @OnClick(R.id.arrow)
    public void onClick() {

        if (isOpen){

            if (mAppDes.getLineCount() > DEFAULT_LINE_COUNT){
                mAppDes.setLines(DEFAULT_LINE_COUNT);
                mAppDes.measure(0,0);
            }
            int end = mAppDes.getMeasuredHeight();
            UIUtils.getAnimatViewHeigh(mAppDes,mMeasuredHeight,end);
            UIUtils.rotationView(mArrow,-180f,0);

        }else{

            int start = mAppDes.getHeight();
            UIUtils.getAnimatViewHeigh(mAppDes,start,mMeasuredHeight);
            UIUtils.rotationView(mArrow,0,-180f);

        }
      isOpen = !isOpen;

    }
}





