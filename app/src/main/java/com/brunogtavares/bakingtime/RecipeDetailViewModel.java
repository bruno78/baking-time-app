package com.brunogtavares.bakingtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogtavares on 8/7/18.
 */

public class RecipeDetailViewModel extends ViewModel {

    private final MutableLiveData<Step> mSelectedStep = new MutableLiveData<Step>();
    private Recipe mRecipe;
    private Step mStep;

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

    public void select(Step step) {
        this.mSelectedStep.setValue(step);
    }

    public LiveData<Step> getSelected() {
        return mSelectedStep;
    }


    public Step getStep() {
        return mStep;
    }

    public void setStep(Step step) {
        this.mStep = step;
    }


}
