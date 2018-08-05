package com.brunogtavares.bakingtime.webservice;

import com.brunogtavares.bakingtime.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by brunogtavares on 7/11/18.
 */

public interface BakingTimeService {

    @GET("baking.json")
    Call<List<Recipe>> getAllRecipes();
}
