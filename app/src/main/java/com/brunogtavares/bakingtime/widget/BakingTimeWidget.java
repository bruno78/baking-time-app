package com.brunogtavares.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.brunogtavares.bakingtime.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BakingTimeWidgetConfigureActivity BakingTimeWidgetConfigureActivity}
 */
public class BakingTimeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);

        String ingredientsJson = BakingTimeWidgetConfigureActivity.loadIngredientsPref(context, appWidgetId);
        String recipeName = BakingTimeWidgetConfigureActivity.loadRecipeNamePref(context, appWidgetId);

        String emptyOven = context.getString(R.string.appwidget_text);
        if (!ingredientsJson.equals(emptyOven)) {

            views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);

            Intent serviceIntent = new Intent(context, BakingTimeWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.putExtra(BakingTimeWidgetService.WIDGET_INGREDIENTS, ingredientsJson);
            serviceIntent.putExtra(BakingTimeWidgetService.WIDGET_RECIPE_NAME, recipeName);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.lv_ingredient_widget_listview, serviceIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            BakingTimeWidgetConfigureActivity.deleteRecipePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

