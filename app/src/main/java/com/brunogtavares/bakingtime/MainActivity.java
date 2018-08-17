package com.brunogtavares.bakingtime;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.utils.NetworkUtils;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.brunogtavares.bakingtime.RecipeDetailActivity.RECIPE_BUNDLE_KEY;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private RecipeAdapter mRecipeAdapter;
    private MainViewModel mViewModel;
    private List<Recipe> mRecipeList;

    @BindView(R.id.rv_recipe_list) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Planting Timber
        if(BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        // Bind views with ButterKnife
        ButterKnife.bind(this);

        // This will help to cache the viewholders and improve performance
        mRecyclerView.setItemViewCacheSize(6);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Create a new adapter that takes an empty list of movies as input
        // Initialize the list to later populate it.
        mRecipeList = new ArrayList<>();
        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeAdapter.setContext(getApplicationContext());
        mRecipeAdapter.setRecipeList(mRecipeList);

        mRecyclerView.setAdapter(mRecipeAdapter);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        boolean isConnected = NetworkUtils.checkForNetworkStatus(getApplicationContext());
        if (isConnected) {
            initViewModel();
        }

    }

    @Override
    public void onClick(Recipe recipe) {
        Timber.v("Recipe to be send in the intent: " + recipe.toString());
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_BUNDLE_KEY, recipe);
        startActivity(intent);
    }

    private void initViewModel() {
        mViewModel.getRecipeList().observe(this, new android.arch.lifecycle.Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                mRecipeList.clear();
                mRecipeList = recipes;
                mRecipeAdapter.setRecipeList(mRecipeList);
            }
        });
    }
}
