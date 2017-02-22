package com.dominic.googleplay.ui.fragment;

import android.view.View;
import android.widget.Toast;

import com.dominic.googleplay.R;
import com.dominic.googleplay.adapter.RecommendAdapter;
import com.dominic.googleplay.network.Api;
import com.dominic.googleplay.network.HeiMaRetrofit;
import com.dominic.googleplay.widget.StellarMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class RecommendFragment extends BaseFragment {


    private List<String> mRecommendList;

    @Override
    protected void startLoadData() {

        Api api = HeiMaRetrofit.getInstance().getApi();
        Call<List<String>> listRecommend = api.listRecommend();
        listRecommend.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                mRecommendList = response.body();
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(), "网络请求数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected View onCreateConentView() {
        StellarMap stellarMap = new StellarMap(getContext());
        stellarMap.setAdapter(new RecommendAdapter(getContext(),mRecommendList));
        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        stellarMap.setRegularity(15,20);   //设置网格
        stellarMap.setGroup(0,false);   //设置初始化组
        return stellarMap;
    }
}
