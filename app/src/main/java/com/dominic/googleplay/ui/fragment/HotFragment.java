package com.dominic.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dominic.googleplay.R;
import com.dominic.googleplay.network.HeiMaRetrofit;
import com.dominic.googleplay.widget.FlowLayout;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dominic on 2017/2/15.
 */
public class HotFragment extends BaseFragment {

    private List<String> mLoadData;

    @Override
    protected void startLoadData() {
        Call<List<String>> listCall = HeiMaRetrofit.getInstance().getApi().listHot();
        listCall.enqueue(new Callback<List<String>>() {

            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                mLoadData = response.body();
                loadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(), "网络请求数据失败", Toast.LENGTH_SHORT).show();
                loadDataFail();
            }
        });

    }

    @Override
    protected View onCreateConentView() {
        ScrollView scrollView = new ScrollView(getContext());
        FlowLayout flowLayout =new FlowLayout(getContext());
        int padding = getResources().getDimensionPixelOffset(R.dimen.padding);
        flowLayout.setPadding(padding,padding,padding,padding);
        float textSize = getResources().getDimensionPixelSize(R.dimen.textsize);

        for (int i = 0; i < mLoadData.size(); i++) {
            TextView textView = getTextView(padding, textSize, i);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), mLoadData.get(finalI), Toast.LENGTH_SHORT).show();
                }
            });

            StateListDrawable stateListDrawable = getStateListDrawable();
            textView.setBackgroundDrawable(stateListDrawable); //设置选择器

            flowLayout.addView(textView);
        }

        scrollView.addView(flowLayout);
        return scrollView;
    }


    public StateListDrawable getStateListDrawable(){
       GradientDrawable normal = new GradientDrawable();
        int argb = getRandomColor();
        normal.setColor(argb);
        normal.setCornerRadius(8);

        GradientDrawable press = new GradientDrawable();
        press.setCornerRadius(8);
        press.setColor(Color.DKGRAY);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},press);
        stateListDrawable.addState(new int[]{},normal);

        return stateListDrawable;

    }


    private int getRandomColor() {
        Random random = new Random();
        int alpha = 250;
        int red = 30 + random.nextInt(190);
        int green= 30 + random.nextInt(190);
        int bule= 30 + random.nextInt(190);
        return Color.argb(alpha,red,green,bule);
    }

    @NonNull
    private TextView getTextView(int padding, float textSize, int i) {
        TextView textView  = new TextView(getContext());
        textView.setText(mLoadData.get(i));
        textView.setTextSize(textSize);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(padding, padding, padding, padding);//设置padding
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        return textView;
    }
}
