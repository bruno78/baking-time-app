package com.brunogtavares.bakingtime;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.webservice.BakingTimeRepository;

import java.util.List;

/**
 * Created by brunogtavares on 8/4/18.
 */

public class MainViewModel extends AndroidViewModel {

    private List<Recipe> mRecipeList;
    private BakingTimeRepository mRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepo = BakingTimeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipeList() {

        MutableLiveData<List<Recipe>> mutableRecipeList = new MutableLiveData<>();

        if(mRecipeList == null) {
            mutableRecipeList = mRepo.getAllRecipes();
        }
        else {
            // Get list from savedInstanceState;
            mutableRecipeList.setValue(mRecipeList);
        }

        if(mutableRecipeList == null) {
            mutableRecipeList = mRepo.getAllRecipes();
        }

        return mutableRecipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
    }
}
