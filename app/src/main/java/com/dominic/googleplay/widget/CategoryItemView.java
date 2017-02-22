package com.dominic.googleplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dominic.googleplay.R;
import com.dominic.googleplay.bean.CategoryBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dominic on 2017/2/16.
 */

public class CategoryItemView extends RelativeLayout {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.table_layout)
    TableLayout mTableLayout;

    public CategoryItemView(Context context) {
        this(context,null);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_category_item, this);
        ButterKnife.bind(this, this);
    }


    public void bindView(CategoryBean categoryBean) {

        //由于listView回收item的原因
        mTableLayout.removeAllViews();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int maxWidth = screenWidth - mTableLayout.getPaddingLeft() - mTableLayout.getPaddingRight();
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.width = maxWidth / 3;

        mTitle.setText(categoryBean.title);
        for (int i = 0; i < categoryBean.infos.size(); i++) {
            TableRow tableRow = new TableRow(getContext());
            CategoryBean.InfosBean infosBean = categoryBean.infos.get(i);

            addFirstView(layoutParams,tableRow, infosBean);
            addSecondView(layoutParams,tableRow, infosBean);
            addThirdView(layoutParams,tableRow, infosBean);

            mTableLayout.addView(tableRow);
        }
    }

    private void addFirstView(TableRow.LayoutParams layoutParams, TableRow tableRow, CategoryBean.InfosBean infosBean) {
        if (infosBean.name1.length() > 0){
            CategoryInfoItemView categoryInfoItemView1 = new CategoryInfoItemView(getContext());
            categoryInfoItemView1.bindView(infosBean.name1,infosBean.url1);
            categoryInfoItemView1.setLayoutParams(layoutParams);
            tableRow.addView(categoryInfoItemView1);
        }
    }

    private void addSecondView(TableRow.LayoutParams layoutParams, TableRow tableRow, CategoryBean.InfosBean infosBean) {
        if (infosBean.name2.length()>0){
            CategoryInfoItemView categoryInfoItemView2 = new CategoryInfoItemView(getContext());
            categoryInfoItemView2.bindView(infosBean.name2,infosBean.url2);
            categoryInfoItemView2.setLayoutParams(layoutParams);
            tableRow.addView(categoryInfoItemView2);
        }
    }

    private void addThirdView(TableRow.LayoutParams layoutParams, TableRow tableRow, CategoryBean.InfosBean infosBean) {
        if (infosBean.name3.length() > 0){
            CategoryInfoItemView categoryInfoItemView3 = new CategoryInfoItemView(getContext());
            categoryInfoItemView3.bindView(infosBean.name3,infosBean.url3);
            categoryInfoItemView3.setLayoutParams(layoutParams);
            tableRow.addView(categoryInfoItemView3);
        }
    }
}
