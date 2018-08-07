package com.brunogtavares.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_KEY";

    private List<Step> mStepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            Recipe recipe = intent.getExtras().getParcelable(RECIPE_BUNDLE_KEY);
            mStepList = recipe.getStepList();
        }
    }
}
