package com.brunogtavares.bakingtime.ui.MainActivity;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Recipe;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogtavares on 8/3/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList;
    private Context mContext;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter (RecipeAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = mRecipeList.get(position);

        String title = recipe.getName();
        String servings = String.valueOf(recipe.getServings());
        String imageUrl = recipe.getImage();

        if(TextUtils.isEmpty(imageUrl)) {
            // insert an image place holder
            holder.mImageCover.setImageResource(R.drawable.oven_mitten_round);
        }
        else {
            // if there's an image, we use glide to insert that image.
            Uri uri = Uri.parse(imageUrl).buildUpon().build();
            Glide.with(mContext)
                    .load(uri)
                    .into(holder.mImageCover);
        }

        holder.mRecipeTitle.setText(title);
        holder.mServingQty.setText(servings);

    }

    @Override
    public int getItemCount() {
        return mRecipeList != null ? mRecipeList.size() : 0;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }


    public void setRecipeList(List<Recipe> recipeList) {
        this.mRecipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_recipe_image_cover) ImageView mImageCover;
        @BindView(R.id.tv_recipe_title) TextView mRecipeTitle;
        @BindView(R.id.tv_serving_quantity) TextView mServingQty;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            mClickHandler.onClick(mRecipeList.get(position));
        }
    }
}
