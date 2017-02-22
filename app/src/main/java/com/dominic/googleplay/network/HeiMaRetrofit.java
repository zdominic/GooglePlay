package com.dominic.googleplay.network;

import android.content.Context;

import com.dominic.googleplay.app.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dominic on 2017/2/15.
 */

public class HeiMaRetrofit {

    private static HeiMaRetrofit sHeiMaRetrofit;

    private Gson mGson = new GsonBuilder().setLenient().create(); //设置宽大处理畸形gson

    private Api mApi;

    private long CACHE_SIZE = 5 * 1024 * 1024;

    private HeiMaRetrofit() {
    }


    public void init(Context context) {

        String directoryDir = context.getCacheDir().getAbsolutePath() + "/response";
        File file = new File(directoryDir);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(file, CACHE_SIZE))
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR) //拦截器，打印请求头和响应头
                .build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create(mGson)).
                build();
        mApi = retrofit.create(Api.class);
    }


    public static HeiMaRetrofit getInstance() {
        if (sHeiMaRetrofit == null) {
            synchronized (HeiMaRetrofit.class) {
                if (sHeiMaRetrofit == null) {
                    sHeiMaRetrofit = new HeiMaRetrofit();
                }
            }
        }
        return sHeiMaRetrofit;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            //设置5分钟后缓存过期
            CacheControl.Builder builder = new CacheControl.Builder().maxAge(5, TimeUnit.MINUTES);
            return originalResponse.newBuilder()
                    .header("Cache-Control", builder.build().toString())
                    .build();
        }
    };


    public Api getApi() {
        return mApi;
    }

}

