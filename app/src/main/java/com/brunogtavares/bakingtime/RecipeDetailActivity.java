package com.brunogtavares.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brunogtavares.bakingtime.model.Recipe;

import java.util.List;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.SelectStep {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_KEY";

    IngredientAndStepFragment mIngredientAndStepFragment;
    FragmentManager mFragmentManager;
    private Recipe mRecipe;

    // TODO: FINISH Implementing a click on the IngredientAndStep fragment and also the Exoplayer.
    // TODO: Widget, Different devices, Test, Change the title of the activity for the recipe, Improve UI.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mIngredientAndStepFragment = new IngredientAndStepFragment();
        mIngredientAndStepFragment.setSelectStep(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
            Timber.i("RECIPE SIZE: " + mRecipe.toString());

        }

        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.fl_ingredient_and_step_container, mIngredientAndStepFragment)
                .commit();
    }

    @Override
    public void stepSelected() {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_ingredient_and_step_container, stepDetailFragment)
                .commit();
    }
}
