package com.dominic.googleplay.adapter;

import android.content.Context;

import com.dominic.googleplay.widget.LoadingMoreProgressView;

import java.util.List;

/**
 * Created by Dominic on 2017/2/16.
 */

public abstract class BaseLoadMoreListAdapter<T> extends BaseListAdapter<T> {

    private final int ITEM_NORMAL = 0;
    private final int ITEM_PROGRESS = 1;

    private final Context mContext;
    private final List<T> mDataList;

    public BaseLoadMoreListAdapter(Context context, List<T> dataList) {
        super(context, dataList);
        mContext = context;
        mDataList = dataList;
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == ITEM_NORMAL){
            onBindNormalViewHoler(viewHolder,position);
        }

    }


    /*
    * 子类必须去实现Normal viewholder
    * */
    protected abstract void onBindNormalViewHoler(ViewHolder viewHolder, int position);

    @Override
    public ViewHolder onCreatViewHolder(int position) {
        if (getItemViewType(position) == ITEM_PROGRESS) {
            return new ViewHolder(new LoadingMoreProgressView(mContext));
        } else {

            return onCreatNormalViewHolder();
        }
    }

    protected abstract ViewHolder onCreatNormalViewHolder(); //让子类去实现各自不同的item

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return ITEM_PROGRESS;
        } else {
            return ITEM_NORMAL;
        }
    }
}
