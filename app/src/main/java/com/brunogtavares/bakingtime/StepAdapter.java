package com.brunogtavares.bakingtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogtavares on 8/7/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> mStepsList;
    private List<Ingredient> mIngredientList;

    private final StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler {
        void onClick(Step step);
    }

    public StepAdapter (StepsAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);

        return new StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        Step step = mStepsList.get(position);

        String stepOrder = String.valueOf(step.getId() + 1);
        String shorDescription = step.getShortDescription();
        String stepString = stepOrder + ". " + shorDescription;

        holder.mStepTextView.setText(stepString);

    }

    @Override
    public int getItemCount() {
        return mStepsList != null ? mStepsList.size() : 0;
    }

    public void setStepList(List<Step> stepList) {
        this.mStepsList = stepList;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_steps_list_item) TextView mStepTextView;

        public StepViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            mClickHandler.onClick(mStepsList.get(position));
        }
    }
}
