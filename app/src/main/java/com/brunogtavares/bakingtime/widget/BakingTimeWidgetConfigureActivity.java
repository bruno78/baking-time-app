package com.brunogtavares.bakingtime.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.networkservice.BakingTimeRepository;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * The configuration screen for the {@link BakingTimeWidget BakingTimeWidget} AppWidget.
 */
public class BakingTimeWidgetConfigureActivity extends AppCompatActivity implements BakingTimeWidgetConfigureAdapter.OnWidgetSelectorHandler {

    private static final String PREFS_NAME = "com.brunogtavares.bakingtime.widget.BakingTimeWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_INGREDIENTS_KEY = "_ingredients";
    private static final String PREF_RECIPE_NAME_KEY = "_name";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public BakingTimeWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveIngredientsPref(Context context, int appWidgetId, List<Ingredient> ingredients, String recipeName) {

        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId + PREF_INGREDIENTS_KEY, ingredientsToString(ingredients));
        prefs.putString(PREF_PREFIX_KEY + appWidgetId + PREF_RECIPE_NAME_KEY, recipeName);
        prefs.apply();
    }

    private static String ingredientsToString(List<Ingredient> ingredients){
        Gson gson = new Gson();
        return gson.toJson(ingredients);
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadIngredientsPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + PREF_INGREDIENTS_KEY, context.getString(R.string.appwidget_text));
    }

    static String loadRecipeNamePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + PREF_RECIPE_NAME_KEY, context.getString(R.string.app_name));
    }

    static void deleteRecipePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.baking_time_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        BakingTimeWidgetConfigureAdapter adapter = new BakingTimeWidgetConfigureAdapter(this);
        adapter.setContext(this);

        RecyclerView recyclerView = findViewById(R.id.rv_widget_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        BakingTimeRepository repo = BakingTimeRepository.getInstance();
        repo.getAllRecipes().observe(this, adapter::setRecipeList);
    }

    @Override
    public void onWidgetSelectorClick(Recipe recipe) {

        final Context context = BakingTimeWidgetConfigureActivity.this;

        saveIngredientsPref(context, mAppWidgetId, recipe.getIngredients(), recipe.getName());

        // It is the responsibility of the configuration activity to update the app widget.
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingTimeWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }

}

