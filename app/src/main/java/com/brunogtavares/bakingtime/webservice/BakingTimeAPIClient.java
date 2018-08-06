package com.brunogtavares.bakingtime.webservice;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brunogtavares on 7/10/18.
 */

public class BakingTimeAPIClient {

    private static final Object LOCK = new Object();
    private static Retrofit sInstance;
    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static Retrofit getRetrofitInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return sInstance;
    }

}
