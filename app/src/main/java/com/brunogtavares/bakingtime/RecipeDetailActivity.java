package com.brunogtavares.bakingtime;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.OnStepClickListener {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_KEY";
    public  static final String SAVED_STEP = "SAVED_STEP";

    private IngredientAndStepFragment mIngredientAndStepFragment;
    private FragmentManager mFragmentManager;
    private Recipe mRecipe;
    private Step mStep;

    // TODO: FINISH Exoplayer.
    // DONE: Implementing a click on the IngredientAndStep fragment and also
    // TODO: Widget, Different devices, Test, Change the title of the activity for the recipe, Improve UI.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get action bar and set back menu
        ActionBar actionBar = getSupportActionBar();

        // Enable up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing Fragement manager
        mFragmentManager = getSupportFragmentManager();

        mIngredientAndStepFragment = new IngredientAndStepFragment();
        mIngredientAndStepFragment.setSelectStep(this);

        if (savedInstanceState == null) {

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
                mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
                Timber.d("RECIPE SIZE: " + mRecipe.toString());
            }

            mFragmentManager.beginTransaction()
                    .add(R.id.fl_ingredient_and_step_container, mIngredientAndStepFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            mRecipe = savedInstanceState.getParcelable(RECIPE_BUNDLE_KEY);
        }

        if (mRecipe != null && actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
        }

    }

    @Override
    public  void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_BUNDLE_KEY, mRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.containsKey(SAVED_STEP)) {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
            initViewModel();
        }

    }


    // To be used on onRestoreInstanceState
    private void initViewModel() {

        Timber.d("STEP: " + mStep.getShortDescription());

        RecipeDetailViewModel recipeDetailViewModel = new RecipeDetailViewModel();
        ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        recipeDetailViewModel.select(mStep);
    }

    // Method from Interface created at IngredientAndStep Fragment
    @Override
    public void stepSelected() {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        mFragmentManager.beginTransaction()
                .replace(R.id.fl_ingredient_and_step_container, stepDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 2) {
            getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return super.onSupportNavigateUp();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 2) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            this.finish();
        }
    }
}
