package com.brunogtavares.bakingtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogtavares on 8/7/18.
 */

public class RecipeDetailViewModel extends ViewModel {

    private Recipe mRecipe;
    private final MutableLiveData<Step> mSelected = new MutableLiveData<>();

    // this will provide data for ingredients and steps
    public void setRecipe(Recipe recipe) {
        this.mRecipe = recipe;
    }


    public List<Object> getIngredientAndStepList() {

        List<Object> ingredientAndStepList = new ArrayList<>();

        if (mRecipe != null) {
            ingredientAndStepList.addAll(mRecipe.getIngredients());
            ingredientAndStepList.addAll(mRecipe.getSteps());
        }
        return ingredientAndStepList;
    }

    // this will all the step data be shared between fragments
    public void selectStep(Step step) {
        mSelected.setValue(step);
    }

    public LiveData<Step> getSelected() {
        return mSelected;
    }

}
