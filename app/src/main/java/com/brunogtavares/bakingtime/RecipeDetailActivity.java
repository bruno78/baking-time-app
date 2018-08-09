package com.brunogtavares.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_KEY";

    private Recipe mRecipe;
    private List<Object> mRecipeDetailLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
            Timber.i("RECIPE SIZE: " + mRecipe.toString());

        }

        RecipeDetailListFragment ingredientsFragment = new RecipeDetailListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fl_recipe_items_container, ingredientsFragment)
                .commit();


    }

}
