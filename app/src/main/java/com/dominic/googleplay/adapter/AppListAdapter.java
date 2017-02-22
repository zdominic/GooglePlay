package com.dominic.googleplay.adapter;

import android.content.Context;

import com.dominic.googleplay.bean.AppListItemBean;
import com.dominic.googleplay.widget.AppListItemView;

import java.util.List;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppListAdapter extends BaseLoadMoreListAdapter<AppListItemBean> {


    public AppListAdapter(Context context, List<AppListItemBean> dataList) {
        super(context, dataList);

    }

    @Override
    protected void onBindNormalViewHoler(ViewHolder viewHolder, int position) {
        ((AppListItemView)viewHolder.mView).bindView(getDataList().get(position));
    }

    @Override
    protected ViewHolder onCreatNormalViewHolder() {
        return new ViewHolder(new AppListItemView(getContext()));
    }





}
