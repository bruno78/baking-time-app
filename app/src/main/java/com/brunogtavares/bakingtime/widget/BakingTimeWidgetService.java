package com.brunogtavares.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.utils.RecipeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by brunogtavares on 8/16/18.
 * The main purpose of BakingTimeWidget Service is to return BakingTimeWidgetViewsFactory
 * object which further handles the task of filling the widget with appropriate data.
 */

public class BakingTimeWidgetService extends RemoteViewsService {

    public final static String WIDGET_INGREDIENTS = "WIDGET_INGREDIENTS";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        List<Ingredient> ingredientList;

        String ingredientsJson = intent.getStringExtra(WIDGET_INGREDIENTS);

        ingredientList = jsonToIngredientList(ingredientsJson);

        return new BakingTimeWidgetViewsFactory(getApplicationContext(), intent, ingredientList);
    }

    private List<Ingredient> jsonToIngredientList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>(){}.getType();

        return gson.fromJson(json, type);
    }
}

/**
 * This class serves the purpose of an adapter in the widget's context.
 */
class BakingTimeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    private List<Ingredient> mIngredientList;

    public BakingTimeWidgetViewsFactory(Context context, Intent intent, List<Ingredient> ingredients) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        this.mIngredientList = ingredients;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientList != null ? mIngredientList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_time_widget_list_item);

        Ingredient ingredient = mIngredientList.get(position);
        String ingredientString = RecipeUtils.getIngredientString(ingredient);
        views.setTextViewText(R.id.tv_ingredient_widget_list_item, ingredientString);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
