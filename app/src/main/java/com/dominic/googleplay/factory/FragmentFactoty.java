package com.dominic.googleplay.factory;

import android.support.v4.app.Fragment;

import com.dominic.googleplay.ui.fragment.AppFragment;
import com.dominic.googleplay.ui.fragment.CategoryFragment;
import com.dominic.googleplay.ui.fragment.GameFragment;
import com.dominic.googleplay.ui.fragment.HomeFragment;
import com.dominic.googleplay.ui.fragment.HotFragment;
import com.dominic.googleplay.ui.fragment.RecommendFragment;
import com.dominic.googleplay.ui.fragment.SubjectFragment;

/**
 * Created by Dominic on 2017/2/15.
 */

public class FragmentFactoty {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_APP = 1;
    private static final int FRAGMENT_GAME = 2;
    private static final int FRAGMENT_SUBJECT = 3;
    private static final int FRAGMENT_RECOMMEND = 4;
    private static final int FRAGMENT_CATEGORY = 5;
    private static final int FRAGMENT_HOT = 6;

    public static FragmentFactoty fragmentFactoty;

    private FragmentFactoty(){}

    public static FragmentFactoty getInstance() {
        if (fragmentFactoty == null) {
            synchronized (FragmentFactoty.class) {
                if (fragmentFactoty == null) {
                    fragmentFactoty = new FragmentFactoty();
                }
            }
        }
        return fragmentFactoty;
    }

    public static Fragment getFragment(int position){
        switch (position) {
            case FRAGMENT_HOME:
                return new HomeFragment();

            case FRAGMENT_APP:
                return new AppFragment();

            case FRAGMENT_GAME:
                return new GameFragment();

            case FRAGMENT_SUBJECT:
                return new SubjectFragment();

            case FRAGMENT_RECOMMEND:
                return new RecommendFragment();

            case FRAGMENT_CATEGORY:
                return new CategoryFragment();

            case FRAGMENT_HOT:
                return new HotFragment();

        }
        return null;
    }

}
