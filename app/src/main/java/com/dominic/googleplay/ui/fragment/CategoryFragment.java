package com.dominic.googleplay.ui.fragment;

import android.widget.BaseAdapter;

import com.dominic.googleplay.adapter.CategoryAdapter;
import com.dominic.googleplay.bean.CategoryBean;
import com.dominic.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class CategoryFragment extends BaseListFragment {


    private List<CategoryBean> mCategoryBeanList;

    @Override
    protected void startLoadData() {
        Call<List<CategoryBean>> listCall =
                HeiMaRetrofit.getInstance().getApi().listCategory();
        listCall.enqueue(new Callback<List<CategoryBean>>() {
            @Override
            public void onResponse(Call<List<CategoryBean>> call, Response<List<CategoryBean>> response) {
                mCategoryBeanList = response.body();
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<CategoryBean>> call, Throwable t) {
                loadDataFail();
            }
        });
    }


    @Override
    public BaseAdapter onCreateAdatper() {
        return new CategoryAdapter(getContext(),mCategoryBeanList);
    }
}
