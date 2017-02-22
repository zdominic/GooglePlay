package com.dominic.googleplay.ui.fragment;

import android.widget.BaseAdapter;

import com.dominic.googleplay.adapter.SubjectAdapter;
import com.dominic.googleplay.bean.SubjectBean;
import com.dominic.googleplay.network.HeiMaRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class SubjectFragment extends BaseLoadMoreListFragment {


    private List<SubjectBean> mSubjectBeanList;

    @Override
    public BaseAdapter onCreateAdatper() {
        return new SubjectAdapter(getContext(),mSubjectBeanList);
    }

    @Override
    protected void startLoadData() {
        Call<List<SubjectBean>> listSubject = HeiMaRetrofit.getInstance().getApi().listSubject(0);
        listSubject.enqueue(new Callback<List<SubjectBean>>() {
            @Override
            public void onResponse(Call<List<SubjectBean>> call, Response<List<SubjectBean>> response) {
                loadDataSuccess();
                mSubjectBeanList = response.body();
            }

            @Override
            public void onFailure(Call<List<SubjectBean>> call, Throwable t) {
                loadDataFail();
            }
        });

    }

    @Override
    protected void startLoadMoreData() {

        Call<List<SubjectBean>> listCall = HeiMaRetrofit.getInstance().getApi().listSubject(mSubjectBeanList.size());
        listCall.enqueue(new Callback<List<SubjectBean>>() {
            @Override
            public void onResponse(Call<List<SubjectBean>> call, Response<List<SubjectBean>> response) {
                loadDataSuccess();
                mSubjectBeanList.addAll(response.body());
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectBean>> call, Throwable t) {
                loadDataFail();
            }
        });
    }
}
