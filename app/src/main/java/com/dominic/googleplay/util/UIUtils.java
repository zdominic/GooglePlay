package com.dominic.googleplay.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dominic on 2017/2/19.
 */

public class UIUtils {

    /*--------------- 应用详情展开 ---------------*/
    public static void getAnimatViewHeigh(final View view, int start, int end) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    /*--------------- 箭头旋转动画 ---------------*/
    public static void rotationView(View view, float start, float end) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        objectAnimator.start();
    }

}
