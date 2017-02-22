package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.dominic.googleplay.R;

/**
 * Created by Dominic on 2017/2/16.
 */

public class LoadingMoreProgressView extends RelativeLayout {

    public LoadingMoreProgressView(Context context) {
        this(context,null);
    }

    public LoadingMoreProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_loading_more_progress_view,this);
    }
}
