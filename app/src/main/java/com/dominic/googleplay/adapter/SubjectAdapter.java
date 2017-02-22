package com.dominic.googleplay.adapter;

import android.content.Context;

import com.dominic.googleplay.bean.SubjectBean;
import com.dominic.googleplay.widget.SubjectItemView;

import java.util.List;

/**
 * Created by Dominic on 2017/2/16.
 */

public class SubjectAdapter extends BaseLoadMoreListAdapter<SubjectBean> {

    private final Context mContext;
    private final List<SubjectBean> mDataList;

    public SubjectAdapter(Context context, List<SubjectBean> dataList) {
        super(context, dataList);
        mContext = context;
        mDataList = dataList;
    }

    @Override
    protected void onBindNormalViewHoler(ViewHolder viewHolder, int position) {
        ((SubjectItemView)viewHolder.mView).bindView(mDataList.get(position));
    }

    @Override
    protected ViewHolder onCreatNormalViewHolder() {
        return new ViewHolder(new SubjectItemView(mContext));
    }
}
