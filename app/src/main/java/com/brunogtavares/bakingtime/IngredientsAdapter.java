package com.brunogtavares.bakingtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by brunogtavares on 8/7/18.
 */

public class IngredientsAdapter extends  RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public IngredientsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
