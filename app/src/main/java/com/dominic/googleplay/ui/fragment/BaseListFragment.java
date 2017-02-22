package com.dominic.googleplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Dominic on 2017/2/16.
 *
 * 首页，应用，游戏，专题，分类都是listView的形式,抽取一个listView
 */

public abstract class BaseListFragment extends BaseFragment {

    private ListView mListView;

    private BaseAdapter mBaseAdapter;

    @Override
    protected View onCreateConentView() {
        mListView = new ListView(getContext());
        mBaseAdapter = onCreateAdatper();

        View view = onCreateHeadView();
        if (view != null){
            mListView.addHeaderView(view);
        }

        mListView.setAdapter(mBaseAdapter);
        mListView.setDivider(null);
        mListView.setOnItemClickListener(mOnItemClickListener);
        initListView();
        return mListView;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            onListViewItemClick(position);
        }
    };

    /**
     *  处理ListView 的item的点击事件，子类可以覆写该方法实现自己的点击
     */

    protected void onListViewItemClick(int position) {

    }

    protected View onCreateHeadView() {
        return null;
    }


    public void initListView() {
    }

    public abstract BaseAdapter onCreateAdatper();


    public ListView getListView() {
        return mListView;
    }

    public BaseAdapter getAdapter() {
        return mBaseAdapter;
    }

}
