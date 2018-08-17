package com.brunogtavares.bakingtime.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogtavares on 8/16/18.
 */

public class BakingTimeWidgetConfigureAdapter extends RecyclerView.Adapter<BakingTimeWidgetConfigureAdapter.RecipeWidgetViewHolder> {

    private List<Recipe> mRecipeList;
    private Context mContext;
    private OnWidgetSelectorHandler mWidgetHandler;

    public interface OnWidgetSelectorHandler {
        void onWidgetSelectorClick(Recipe recipe);
    }

    BakingTimeWidgetConfigureAdapter(OnWidgetSelectorHandler widgetHandler) {
        this.mWidgetHandler = widgetHandler;
    }

    @NonNull
    @Override
    public RecipeWidgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.baking_time_widget_configure_list_item, parent, false);
        return new RecipeWidgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeWidgetViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.mRecipeTitle.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeList != null ? mRecipeList.size() : 0;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public class RecipeWidgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_widget_recipe_title) TextView mRecipeTitle;

        public RecipeWidgetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mWidgetHandler.onWidgetSelectorClick(mRecipeList.get(position));
        }
    }
}
