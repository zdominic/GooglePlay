package com.dominic.googleplay.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dominic.googleplay.R;

import butterknife.BindView;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Dominic on 2017/2/18.
 */

public class AppDetailActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_app_detail;
    }

    @Override
    protected void init() {
        super.init();
        ShareSDK.initSDK(getApplicationContext(),"1b7755595260c");

        setStatusBarColor();
        Intent intent = getIntent();
        String package_name = intent.getStringExtra("package_name");

        setSupportActionBar(mToolBar);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("应用详情");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return true;
    }


    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
