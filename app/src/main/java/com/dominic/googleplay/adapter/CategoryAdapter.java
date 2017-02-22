package com.dominic.googleplay.adapter;

import android.content.Context;

import com.dominic.googleplay.bean.CategoryBean;
import com.dominic.googleplay.widget.CategoryItemView;

import java.util.List;

/**
 * Created by Dominic on 2017/2/16.
 */

public class CategoryAdapter extends BaseListAdapter<CategoryBean> {


    private final Context mContext;
    private final List<CategoryBean> mDataList;

    public CategoryAdapter(Context context, List<CategoryBean> dataList) {
        super(context, dataList);

        mContext = context;
        mDataList = dataList;
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int position) {
        ((CategoryItemView)viewHolder.mView).bindView(getDataList().get(position));
    }

    @Override
    public ViewHolder onCreatViewHolder(int position) {
        return new ViewHolder(new CategoryItemView(mContext));
    }
}
