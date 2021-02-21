package com.thesaugat.instafeed.utils;

import com.google.gson.GsonBuilder;
import com.thesaugat.instafeed.api.ApiServices;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final int TIME_OUT = 30;


    public static ApiServices getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
//                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(interceptor).build()
//                ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(okHttpClient)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(ApiServices.class);
    }

}