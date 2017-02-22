package com.dominic.googleplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.dominic.googleplay.widget.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by Dominic on 2017/2/16.
 */

public class RecommendAdapter implements StellarMap.Adapter {

    private final Context mContext;
    private final List<String> mDataList;
    private final int PAGER_SIZE = 15;
    private Random mRandom = new Random();

    public RecommendAdapter(Context context, List<String> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    /*
    * 返回页面的个数
    * */

    @Override
    public int getGroupCount() {
        return PAGER_SIZE;
    }

    /*
    * 每个页面条目的个数
    * @param group 当下组的下标
    * */
    @Override
    public int getCount(int group) {
        if (mDataList.size() % PAGER_SIZE != 0) {
            if (group == getGroupCount() - 1) {
                return mDataList.size() % PAGER_SIZE;
            }
        }
        return PAGER_SIZE;
    }

    /*
    * 每个条目的视图
    * */
    @Override
    public View getView(int group, int position, View convertView) {
        TextView textView = null;
        if (convertView == null) {
            textView = new TextView(mContext);
        } else {
            textView = (TextView) convertView;
        }

        String s = mDataList.get(position);
        textView.setText(s);
        textView.setTextColor(getRandomColor());
        textView.setTextSize(mRandom.nextInt(4) + 14);
        return textView;
    }


    @Override
    public int getNextGroupOnPan(int group, float degree) {
        return 0;
    }


    /**
     * 返回放大或者缩小下一组的下标
     *
     * @param group 当前组的下标
     * @param isZoomIn true表示放大，false表示缩小
     */
    @Override
    public int getNextGroupOnZoom(int group, boolean isZoomIn) {
        if (isZoomIn) {
            return (group + 1) % getGroupCount();
        } else {
            return (group - 1 + getGroupCount()) % getGroupCount();
        }
    }

    private int getRandomColor() {
        int alpha = 250;
        int red = 30 + mRandom.nextInt(190);
        int green = 30 + mRandom.nextInt(190);
        int blue = 30 + mRandom.nextInt(190);
        ;
        return Color.argb(alpha, red, green, blue);
    }
}
