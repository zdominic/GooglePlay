package com.dominic.googleplay.ui.fragment;

import android.view.View;

import com.dominic.googleplay.app.Constants;
import com.dominic.googleplay.bean.HomeBean;
import com.dominic.googleplay.network.HeiMaRetrofit;
import com.leon.loopviewpagerlib.FunBanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class HomeFragment extends BaseAppListFragment {

    private List<String> mPicture;

    @Override
    protected void startLoadData() {
        Call<HomeBean> homeBeanCall = HeiMaRetrofit.getInstance().getApi().listHome(0);
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                mPicture = response.body().picture;
                getDataList().addAll(response.body().list);
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                loadDataFail();
            }
        });
    }


    @Override
    protected void startLoadMoreData() {
        Call<HomeBean> homeBeanCall = HeiMaRetrofit.getInstance().getApi().listHome(getDataList().size());
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {

                getDataList().addAll(response.body().list);
                getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected View onCreateHeadView() {
        FunBanner funBanner = new FunBanner(getContext());
        funBanner.setEnableAutoLoop (true);//设置自动轮播 要放在        funBanner.setImageUrls(mPicture);之前
        funBanner.setImageUrlHost(Constants.BASE_IMAGE_URL);
        funBanner.setImageUrls(mPicture);
        //设置图片的高宽比 宽度全屏，它会根据宽高比设置轮播图的高度
        funBanner.setRatio(0.377f);
        return funBanner;

    }


    @Override
    protected String getPackageName(int position) {
        return getDataList().get(position - 1).packageName;
    }
}
