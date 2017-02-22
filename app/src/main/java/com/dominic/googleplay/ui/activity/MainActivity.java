package com.dominic.googleplay.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.dominic.googleplay.R;
import com.dominic.googleplay.adapter.MainPagerAdapter;
import com.dominic.googleplay.network.DownloadManager;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.activity_drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        initActionBar();

        String[] titles = getResources().getStringArray(R.array.main_titles);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),titles));

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mNavigationView.setCheckedItem(item.getItemId());
                return true;
            }
        });
        
        //andorid 6.0之后需要加权限
        checkWriteExternalStoragePermmision();
        

    }

    private void checkWriteExternalStoragePermmision() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_DENIED){
            
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

    }

    private void initActionBar() {

        setSupportActionBar(mToolBar);

        ActionBar supportActionBar = getSupportActionBar();

        supportActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮是否可见
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActionBarDrawerToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                DownloadManager.getInstance().creatDir();
            }else {
                Toast.makeText(getApplicationContext(), "权限未同意,无法下载", Toast.LENGTH_SHORT).show();
                
            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
