package com.brunogtavares.bakingtime;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.webservice.BakingTimeRepository;

import java.util.List;

/**
 * Created by brunogtavares on 8/4/18.
 */

public class MainViewModel extends AndroidViewModel {

    private List<Recipe> mRecipeList;
    private BakingTimeRepository mBakingTimeRepo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mBakingTimeRepo = BakingTimeRepository.getInstance();
        mRecipeList = mBakingTimeRepo.getAllRecipes();
    }

    public List<Recipe> getAllRecipes() {
        return mRecipeList;
    }
}
