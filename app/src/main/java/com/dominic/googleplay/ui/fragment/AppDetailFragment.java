package com.dominic.googleplay.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.network.HeiMaRetrofit;
import com.dominic.googleplay.widget.AppDetailBottomBar;
import com.dominic.googleplay.widget.AppDetailDescriptionView;
import com.dominic.googleplay.widget.AppDetailInfoView;
import com.dominic.googleplay.widget.AppDetailSecurityView;
import com.dominic.googleplay.widget.AppGalleryView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppDetailFragment extends BaseFragment {

    private AppDetailBean mAppDetailBean;

    @Override
    protected void startLoadData() {
        String package_name = getActivity().getIntent().getStringExtra("package_name");
        Call<AppDetailBean> appDetail = HeiMaRetrofit.getInstance().getApi().getAppDetail(package_name);
        appDetail.enqueue(new Callback<AppDetailBean>() {
            @Override
            public void onResponse(Call<AppDetailBean> call, Response<AppDetailBean> response) {
                mAppDetailBean = response.body();
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<AppDetailBean> call, Throwable t) {
                loadDataFail();
            }
        });
    }

    @Override
    protected View onCreateConentView() {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(getContext());

        LinearLayout linearLayoutInScrollView = new LinearLayout(getContext());
        linearLayoutInScrollView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.padding);

        //加app信息
        AppDetailInfoView appDetailInfoView = new AppDetailInfoView(getContext());
        appDetailInfoView.bindView(mAppDetailBean);
        linearLayoutInScrollView.addView(appDetailInfoView);

        //添加app安全信息
        AppDetailSecurityView appDetailSecurityView = new AppDetailSecurityView(getContext());
        appDetailSecurityView.bindView(mAppDetailBean);
        appDetailSecurityView.setLayoutParams(layoutParams);
        linearLayoutInScrollView.addView(appDetailSecurityView);
        //加截图
        AppGalleryView appGalleryView = new AppGalleryView(getContext());
        appGalleryView.bindView(mAppDetailBean);
        appGalleryView.setLayoutParams(layoutParams);
        linearLayoutInScrollView.addView(appGalleryView);
        //加描述
        AppDetailDescriptionView appDetailDescriptionView = new AppDetailDescriptionView(getContext());
        appDetailDescriptionView.bindView(mAppDetailBean);
        appDetailDescriptionView.setLayoutParams(layoutParams);
        linearLayoutInScrollView.addView(appDetailDescriptionView);

        scrollView.addView(linearLayoutInScrollView);

        //scrollview占据除底部条的所有的空间， weight = 1
        LinearLayout.LayoutParams scrollViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
        scrollViewLayoutParams.weight = 1;
        scrollView.setLayoutParams(scrollViewLayoutParams);

        linearLayout.addView(scrollView);

        //添加底部条
        AppDetailBottomBar appDetailBottomBar = new AppDetailBottomBar(getContext());
        appDetailBottomBar.bindView(mAppDetailBean);
        linearLayout.addView(appDetailBottomBar);

        return linearLayout;
    }
}
