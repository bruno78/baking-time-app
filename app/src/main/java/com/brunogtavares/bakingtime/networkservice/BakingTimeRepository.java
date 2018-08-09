package com.brunogtavares.bakingtime.networkservice;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.brunogtavares.bakingtime.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brunogtavares on 8/3/18.
 */

public class BakingTimeRepository {

    private static final String LOG_TAG = BakingTimeRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static BakingTimeRepository sInstance;

    private static BakingTimeService mService;

    private BakingTimeRepository(BakingTimeService service) {
        this.mService = service;
    }

    public static BakingTimeRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                BakingTimeService service = BakingTimeAPIClient.getRetrofitInstance().create(BakingTimeService.class);
                sInstance = new BakingTimeRepository(service);
            }
        }

        return sInstance;
    }

    public MutableLiveData<List<Recipe>> getAllRecipes() {
        final MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

        Call<List<Recipe>> call = mService.getAllRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList.postValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(LOG_TAG, "Unable to retrieve data");
                recipeList.setValue(null);
            }
        });
        return recipeList;
    }

}
