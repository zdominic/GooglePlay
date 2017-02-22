package com.dominic.googleplay.network;

import com.dominic.googleplay.bean.AppDetailBean;
import com.dominic.googleplay.bean.AppListItemBean;
import com.dominic.googleplay.bean.CategoryBean;
import com.dominic.googleplay.bean.HomeBean;
import com.dominic.googleplay.bean.SubjectBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dominic on 2017/2/15.
 */

public interface Api {

    @GET("hot")
    Call<List<String>> listHot();

    @GET("recommend")
    Call<List<String>> listRecommend();

    @GET("category")
    Call<List<CategoryBean>> listCategory();

    @GET("subject")
    Call<List<SubjectBean>> listSubject(@Query("index") int index);

    @GET("game")
    Call<List<AppListItemBean>> listGame(@Query("index") int index);

    @GET("app")
    Call<List<AppListItemBean>> listApp(@Query("index")  int index);

    @GET("home")
    Call<HomeBean> listHome(@Query("index") int index);

    @GET("detail")
    Call<AppDetailBean> getAppDetail(@Query("packageName") String packageName);

}
