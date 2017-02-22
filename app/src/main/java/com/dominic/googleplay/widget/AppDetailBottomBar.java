package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.network.DownloadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Dominic on 2017/2/20.
 */

public class AppDetailBottomBar extends LinearLayout {


    @BindView(R.id.download_button)
    DownloadButton mDownloadButton;
    @BindView(R.id.share)
    Button mShare;
    private AppDetailBean mAppDetailBean;

    public AppDetailBottomBar(Context context) {
        this(context, null);
    }

    public AppDetailBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_bottom_bar, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AppDetailBean appDetailBean) {
        mAppDetailBean = appDetailBean;
        mDownloadButton.syncState(appDetailBean);
    }


    @OnClick({R.id.download_button, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download_button:
                DownloadManager.getInstance().handleDownloadAction(getContext(), mAppDetailBean.packageName);
                break;
            case R.id.share:

                ShareSDK.initSDK(getContext());
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("标题");
                // titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText(mAppDetailBean.name + "  " + mAppDetailBean.des);
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getResources().getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

                // 启动分享GUI
                oks.show(getContext());
                break;
        }
    }
}
