package com.brunogtavares.bakingtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Step;
import com.brunogtavares.bakingtime.utils.RecipeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogtavares on 8/9/18.
 * This is an Adapter with multiple view types. It serves Ingredient type and Step type.
 * To accomplish that, you've got to create a an object list (List<Object>) to be able to add
 * the two types.
 *
 * You need to create a view holder class for each type
 */
public class IngredientAndStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INGREDIENT_TYPE = 0;
    private static final int STEP_TYPE = 1;

    private OnStepClickHandler mStepClickHandler;
    private List<Object> mIngredientAndStepList;

    public interface OnStepClickHandler{
        void onStepClick(Step step);
    }
    public void setOnStepClickHandler(OnStepClickHandler onStepClickHandler) {
        this.mStepClickHandler = onStepClickHandler;
    }

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType) {
            case INGREDIENT_TYPE:
                View ingredientView = inflater.inflate(R.layout.ingredients_list_item, parent, false);
                viewHolder = new IngredientViewHolder(ingredientView);
                break;
            case STEP_TYPE:
                View stepView = inflater.inflate(R.layout.steps_list_item, parent, false);
                viewHolder = new StepViewHolder(stepView);
                break;
            default:
                // TODO: Fix this
                View defaultView = inflater.inflate(R.layout.steps_list_item, parent, false);
                viewHolder = new StepViewHolder(defaultView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case INGREDIENT_TYPE:
                IngredientViewHolder ingredientViewHolder = (IngredientViewHolder) holder;
                Ingredient ingredient = (Ingredient) mIngredientAndStepList.get(position);
                if(ingredient != null) {
                    ingredientViewHolder.mIngredientItem.setText(getIngredientString(ingredient));
                }
                break;
            case STEP_TYPE:
                StepViewHolder stepViewHolder = (StepViewHolder) holder;
                Step step = (Step) mIngredientAndStepList.get(position);
                if(step != null) {
                    stepViewHolder.mStepListItem.setText(getStepString(step));
                }
                break;
            default:
                // TODO FIX THIS

        }
    }

    @Override
    public int getItemCount() {

        return mIngredientAndStepList != null ? mIngredientAndStepList.size() : 0;
    }

    // Helper method to build Ingredient string properly
    private String getIngredientString(Ingredient ingredient) {

        double quantity = ingredient.getQuantity();

        String qty = RecipeUtils.convertQuantity(quantity);
        String measure = RecipeUtils.convertMeasure(ingredient.getMeasure(), quantity);
        String ingredientString = ingredient.getIngredient();

        return qty + measure + ingredientString;

    }

    // Helper method to build Step string properly
    private String getStepString(Step step) {

        return step.getId() == 0 ? step.getShortDescription() :
                step.getId() + ". " + step.getShortDescription();
    }

    /**
     * Ingredient View Holder class
     * Since the ingredients is just the display a list of items it doesn't need a click listener.
     */
    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredients_item) TextView mIngredientItem;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Step View Holder class
     */
    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_steps_list_item) TextView mStepListItem;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Step selectedStep = (Step) mIngredientAndStepList.get(position);
            mStepClickHandler.onStepClick(selectedStep);
        }
    }
}
