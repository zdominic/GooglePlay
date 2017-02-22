package com.dominic.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dominic.googleplay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Dominic on 2017/2/15.
 */

public abstract class BaseFragment extends Fragment {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.load_retry)
    Button mLoadRetry;
    @BindView(R.id.load_failed_container)
    LinearLayout mLoadFailedContainer;
    @BindView(R.id.framelayout_container)
    FrameLayout mFramelayoutContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    protected void init() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        startLoadData();
    }

    /*
    * 子类必须实现的,去加载数据
    * */
    protected abstract void startLoadData();

    /*
    * 加载数据成功
    * */

    protected void loadDataSuccess() {
        mProgressBar.setVisibility(View.GONE);
        mFramelayoutContainer.addView(onCreateConentView());
    }


    /*
    * 创建页面内容的视图,让子类自己去实现
    * */

    protected abstract View onCreateConentView();



    /*
    * 加载数据失败
    * */
    protected void loadDataFail(){
        mProgressBar.setVisibility(View.GONE);
        mLoadFailedContainer.setVisibility(View.VISIBLE);
    }


    /*
    * 重新加载的点击事件
    * */
    @OnClick(R.id.load_retry)
    public void onClick() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadFailedContainer.setVisibility(View.GONE);
        startLoadData();
    }
}
