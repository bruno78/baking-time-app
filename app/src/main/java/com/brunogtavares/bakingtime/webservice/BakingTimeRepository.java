package com.brunogtavares.bakingtime.webservice;

import com.brunogtavares.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by brunogtavares on 8/3/18.
 */

public class BakingTimeRepository {

    private static BakingTimeService mClient;
    private static List<Recipe> mRecipeList;

    private static BakingTimeRepository sInstance;

    private BakingTimeRepository() {
        mClient = BakingTimeAPIClient.getRetrofitInstance().create(BakingTimeService.class);
        mRecipeList = new ArrayList<>();
    }

    public synchronized static BakingTimeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new BakingTimeRepository();
        }

        return sInstance;
    }

    public List<Recipe> getAllRecipes() {

        Call<List<Recipe>> call = mClient.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                mRecipeList = response.body();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                mRecipeList = null;
            }
        });
        return mRecipeList;
    }

}
