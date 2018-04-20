package com.ulrik.provatecnicamobile.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {
    private static FakeApi api;

    public static FakeApi getApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(FakeApi.URL_BASE)
                    .client(new OkHttpClient.Builder().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(FakeApi.class);
        }
        return api;
    }
}
