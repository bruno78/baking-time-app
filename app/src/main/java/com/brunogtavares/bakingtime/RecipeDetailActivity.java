package com.brunogtavares.bakingtime;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity implements IngredientAndStepFragment.SelectStep {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_KEY";
    public  static final String SAVED_STEP = "SAVED_STEP";

    private IngredientAndStepFragment mIngredientAndStepFragment;
    private FragmentManager mFragmentManager;
    private Recipe mRecipe;
    private Step mStep;

    // TODO: FINISH Implementing a click on the IngredientAndStep fragment and also the Exoplayer.
    // TODO: Widget, Different devices, Test, Change the title of the activity for the recipe, Improve UI.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get action bar and set back menu
        ActionBar actionBar = getSupportActionBar();

        mIngredientAndStepFragment = new IngredientAndStepFragment();
        mIngredientAndStepFragment.setSelectStep(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = mFragmentManager.getBackStackEntryCount();
                if (stackHeight > 0) {
                    actionBar.setHomeButtonEnabled(true);
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                else {
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    actionBar.setHomeButtonEnabled(false);
                    backToMainActivity();
                }
            }
        });

        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fl_ingredient_and_step_container, mIngredientAndStepFragment)
                    .addToBackStack(null)
                    .commit();
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
            Timber.i("RECIPE SIZE: " + mRecipe.toString());
        }
        else {
            mRecipe = savedInstanceState.getParcelable(RECIPE_BUNDLE_KEY);
        }

        if (mRecipe != null) {
            actionBar.setTitle(mRecipe.getName());
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mStep = savedInstanceState.getParcelable(SAVED_STEP);
        initViewModel();
    }

    private void backToMainActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    private void initViewModel() {
        RecipeDetailViewModel recipeDetailViewModel = new RecipeDetailViewModel();
        ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        recipeDetailViewModel.setStep(mStep);
    }

    @Override
    public void stepSelected() {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_ingredient_and_step_container, stepDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
