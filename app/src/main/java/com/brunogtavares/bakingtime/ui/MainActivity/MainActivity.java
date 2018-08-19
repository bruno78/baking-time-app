package com.brunogtavares.bakingtime.ui.MainActivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.brunogtavares.bakingtime.BuildConfig;
import com.brunogtavares.bakingtime.IdlingResource.SimpleIdlingResource;
import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.ui.RecipeDetail.RecipeDetailActivity;
import com.brunogtavares.bakingtime.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    // This is for testing using Espresso Idling Resource
    @Nullable
    public SimpleIdlingResource mIdlingResource;

    @Nullable
    @VisibleForTesting
    public SimpleIdlingResource getIdlingResource() {
        return mIdlingResource == null ?
                mIdlingResource = new SimpleIdlingResource() : mIdlingResource;
    }

    private RecyclerView.LayoutManager mLayoutManager;

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


        if (findViewById(R.id.rv_recipe_list) != null) {
            // Bind views with ButterKnife
            ButterKnife.bind(this);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
        }
        else {
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list_tablet);
            mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        }

        // This will help to cache the viewholders and improve performance
        mRecyclerView.setItemViewCacheSize(6);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);

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
        else {
            Toast.makeText(this, getText(R.string.internet_error), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
            startActivity(intent);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Timber.v("Recipe to be send in the intent: " + recipe.toString());
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.RECIPE_BUNDLE_KEY, recipe);
        startActivity(intent);
    }

    private void initViewModel() {
        mViewModel.getRecipeList().observe(this, recipes -> {
            mRecipeList.clear();
            mRecipeList = recipes;
            mRecipeAdapter.setRecipeList(mRecipeList);
        });
    }
}
