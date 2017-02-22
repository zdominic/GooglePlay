package com.dominic.googleplay.ui.fragment;

import android.widget.AbsListView;

/**
 * Created by Dominic on 2017/2/16.
 */

public abstract class BaseLoadMoreListFragment extends BaseListFragment {

    @Override
    public void initListView() {
        super.initListView();
        getListView().setOnScrollListener(mOnScrollListener);
    }

    private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE){
                if (getListView().getLastVisiblePosition() == getListViewCount()){
                    startLoadMoreData();
                }
            }


        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    private int getListViewCount() {
        return getAdapter().getCount() - 1 + getListView().getHeaderViewsCount();
    }

    protected abstract void startLoadMoreData();
}
