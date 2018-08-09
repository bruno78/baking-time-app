package com.brunogtavares.bakingtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Step;

import java.util.List;

/**
 * Created by brunogtavares on 8/9/18.
 */

public class IngredientAndStepAdapter extends
        RecyclerView.Adapter<IngredientAndStepAdapter.IngredientAndStepViewHolder> {

    private static final int INGREDIENT_TYPE = 0;
    private static final int STEP_TYPE = 1;

    private List<Object> mIngredientAndStepList;

    public IngredientAndStepAdapter(List<Object> objectList) {
        this.mIngredientAndStepList = objectList;
    }

    @Override
    public int getItemViewType(int position) {
        if(mIngredientAndStepList.get(position) instanceof Ingredient) {
            return INGREDIENT_TYPE;
        }
        else if (mIngredientAndStepList.get(position) instanceof Step) {
            return STEP_TYPE;
        }
        return -1;
    }


    @NonNull
    @Override
    public IngredientAndStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        IngredientAndStepViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType) {
            case INGREDIENT_TYPE:
                View ingredientView = inflater.inflate(R.layout.ingredient_list_item, parent, false);
                viewHolder = new IngredientAndStepViewHolder(ingredientView);
                break;
            case STEP_TYPE:
                View stepView = inflater.inflate(R.layout.steps_list_item, parent, false);
                viewHolder = new IngredientAndStepViewHolder(stepView);
                break;
            default:
                // TODO: Fix this
                View defaultView = inflater.inflate(R.layout.steps_list_item, parent, false);
                viewHolder = new IngredientAndStepViewHolder(defaultView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAndStepViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case INGREDIENT_TYPE:
                Ingredient ingredient = (Ingredient) mIngredientAndStepList.get(position);
                // TODO: CONTINUE IMPLMENTATION HERE!!!!
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IngredientAndStepViewHolder extends RecyclerView.ViewHolder {
        public IngredientAndStepViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class IngredientsViewHolder extends
}
