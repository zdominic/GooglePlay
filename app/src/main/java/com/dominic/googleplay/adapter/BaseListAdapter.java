package com.dominic.googleplay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Dominic on 2017/2/16.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter{




    private final Context mContext;
    private final List<T> mDataList;

    public BaseListAdapter(Context context, List<T> dataList){
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size() == 0 ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList == null ? null :mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = onCreatViewHolder(position);
            viewHolder.mView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder,position);
        return viewHolder.mView;
    }


    /**
     *
     * @param viewHolder 要绑定的viewholder
     * @param position 要绑定viewholder的下标 通过下标获取对应位置的数据，去绑定holder hold住的view
     */
    protected abstract void onBindViewHolder(ViewHolder viewHolder, int position);


    public abstract ViewHolder onCreatViewHolder(int position);


    public class ViewHolder{
        public View mView;
        public ViewHolder(View view){
            mView = view;
        }
    }


    public List<T> getDataList() {
        return mDataList;
    }

    public Context getContext() {
        return mContext;
    }
}
