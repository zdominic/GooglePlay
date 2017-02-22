package com.dominic.googleplay.ui.fragment;

import android.content.Intent;
import android.widget.BaseAdapter;

import com.dominic.googleplay.adapter.AppListAdapter;
import com.dominic.googleplay.bean.AppListItemBean;
import com.dominic.googleplay.ui.activity.AppDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominic on 2017/2/18.
 */

public abstract class BaseAppListFragment extends BaseLoadMoreListFragment {


    List<AppListItemBean> mDataList = new ArrayList<AppListItemBean>();

    @Override
    public BaseAdapter onCreateAdatper() {
        return new AppListAdapter(getContext(), mDataList);
    }


    public List<AppListItemBean> getDataList() {
        return mDataList;
    }

    @Override
    protected void onListViewItemClick(int position) {
        Intent intent = new Intent(getContext(), AppDetailActivity.class);
        intent.putExtra("package_name", getPackageName(position));
        startActivity(intent);
    }

    protected String getPackageName(int position) {
        return mDataList.get(position).packageName;
    }

    /**
     * 当我们正在下载一个应用的时候，跳转到详情界面，这时候详情界面的DownloadButton设置Observer,把CircleDownloadView的设置的observer覆盖掉了
     * 当从详情界面返回时，我们需要重新同步状态，重新的设置Observer, 需要调用notifyDataSetChanged刷新下列表
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
    }


}