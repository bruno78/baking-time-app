package com.brunogtavares.bakingtime;

import android.arch.lifecycle.ViewModel;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogtavares on 8/7/18.
 * This RecipeDetailViewModel helps to manage views from
 * IngredientAndStep Fragment and RecipeDetail Fragment.
 */

public class RecipeDetailViewModel extends ViewModel {

    private Recipe mRecipe;
    private Step mStep;
    private List<Step> mStepList;

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

    // Get selected step from IngredientAndStep Fragment and displays
    // on Step Detail Fragment
    public Step getSelected() {
        return mStep;
    }

    // Sets the step when it is clicked on IngredientAndStep Fragment
    public void select(Step step) {
        this.mStep = step;
    }


    // retrieve step for next or previous buttons
    public Step getStep(int stepId) {
        return mStepList != null ? mStepList.get(stepId) : null;
    }

    // retrieve number of steps for buttons
    public int getNumberOfSteps() {
        return mStepList != null ? mStepList.size() : 0;
    }

    public void setStepList(List<Step> stepList) {
        this.mStepList = stepList;
    }
}
