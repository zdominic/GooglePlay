package com.dominic.googleplay.ui.fragment;

import com.dominic.googleplay.bean.AppListItemBean;
import com.dominic.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class AppFragment extends BaseAppListFragment {

    @Override
    protected void startLoadMoreData() {
        Call<List<AppListItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listApp(getDataList().size());
        listCall.enqueue(new Callback<List<AppListItemBean>>() {
            @Override
            public void onResponse(Call<List<AppListItemBean>> call, Response<List<AppListItemBean>> response) {
                getDataList().addAll(response.body());
                getAdapter().notifyDataSetChanged();
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<AppListItemBean>> call, Throwable t) {
                loadDataFail();
            }
        });
    }

    @Override
    protected void startLoadData() {
        Call<List<AppListItemBean>> listCall = HeiMaRetrofit.getInstance().getApi().listApp(0);
        listCall.enqueue(new Callback<List<AppListItemBean>>() {
            @Override
            public void onResponse(Call<List<AppListItemBean>> call, Response<List<AppListItemBean>> response) {
                getDataList().addAll(response.body());
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<AppListItemBean>> call, Throwable t) {
                loadDataFail();
            }
        });
    }
}
