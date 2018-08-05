package com.brunogtavares.bakingtime.webservice;

import android.arch.lifecycle.MutableLiveData;

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
    final private static MutableLiveData<List<Recipe>> mRecipeList = new MutableLiveData<>();

    private static BakingTimeRepository sInstance;

    private BakingTimeRepository() {
        mClient = BakingTimeAPIClient.getRetrofitInstance().create(BakingTimeService.class);
    }

    public synchronized static BakingTimeRepository getInstance() {
        if (sInstance == null) {
            sInstance = new BakingTimeRepository();
        }

        return sInstance;
    }

    public MutableLiveData<List<Recipe>> getAllRecipes() {

        Call<List<Recipe>> call = mClient.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipeList.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
        mRecipeList.getValue().size();
        return mRecipeList;
    }

}
