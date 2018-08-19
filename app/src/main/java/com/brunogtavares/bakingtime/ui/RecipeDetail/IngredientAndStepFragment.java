package com.brunogtavares.bakingtime.ui.RecipeDetail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogtavares.bakingtime.R;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 * This frgament displays the Ingredients and Step lists. It uses multi-type view adapter
 * where two elements ingredients and steps are used in the same list to be displayed.
 */
public class IngredientAndStepFragment extends Fragment implements IngredientAndStepAdapter.OnStepClickHandler {

    private static final String LAST_POSITION = "LAST_POSITION";
    private static final String LAST_RECIPE = "LAST_RECIPE";

    private Recipe mRecipe;
    private List<Object> mIngredientAndStepList;

    private RecyclerView mRecyclerView;
    private IngredientAndStepAdapter mIngredientAndStepAdapter;
    private RecipeDetailViewModel mModel;

    private OnStepClickListener mOnStepClickListener;

    @BindView(R.id.tv_recipe_detail_name) TextView mRecipeName;
    @BindView(R.id.tv_recipe_detail_serving_quantity) TextView mRecipeServingsQty;

    /**
     * This assures the Activity has implemented the Callback
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnStepClickListener) {
            mOnStepClickListener = (OnStepClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString() +
                    " must implement OnStepClickListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnStepClickListener = null;
    }

    public void setSelectStep(OnStepClickListener onStepClickListener) {
        this.mOnStepClickListener = onStepClickListener;
    }

    public IngredientAndStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredient_and_step, container, false);

        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(RecipeDetailActivity.RECIPE_BUNDLE_KEY)) {
                mRecipe = intent.getParcelableExtra(RecipeDetailActivity.RECIPE_BUNDLE_KEY);
                Timber.d("RECIPE INFO: " + mRecipe.toString());
            }
        }
        else {
            mRecipe = savedInstanceState.getParcelable(LAST_RECIPE);
        }

        initViewModel();

        mRecipeName.setText(mRecipe.getName());
        mRecipeServingsQty.setText(String.valueOf(mRecipe.getServings()));

        mRecyclerView = rootView.findViewById(R.id.rv_ingredient_and_step_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientAndStepAdapter = new IngredientAndStepAdapter(mIngredientAndStepList);
        mIngredientAndStepAdapter.setOnStepClickHandler(this);
        mRecyclerView.setAdapter(mIngredientAndStepAdapter);

        if(savedInstanceState != null) {
            int position = savedInstanceState.getInt(LAST_POSITION);
            mRecyclerView.scrollToPosition(position);
        }

        return rootView;
    }

    private void initViewModel() {
        // Since viewModel is managing both fragments from the RecipeDetailActivity, you need to call "scope" it
        // by calling getActivity().
        mModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        mModel.setRecipe(mRecipe);
        mModel.setStepList(mRecipe.getSteps());
        // Initializes the list with Object to add ingredients
        // and steps list.
        mIngredientAndStepList = mModel.getIngredientAndStepList();

        Timber.d("INGREDIENTS AND STEPS LIST SIZE: " + mIngredientAndStepList.size());
    }

    // Method from the Ingredient and Step adpater
    @Override
    public void onStepClick(Step step) {
        Timber.d("Step from IngredientAndStep Fragment: " + step.getShortDescription());
        mModel.select(step);
        mOnStepClickListener.stepSelected();
    }

    public interface OnStepClickListener {
        void stepSelected();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        // saves the position of the scrolling list.
        // Since the list only has 4 elements and they almost fit on the screen
        // it's not really necessary but it will be kept here for scalability case
        // (if the number of recipes available grows).
        int lastPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        outState.putInt(LAST_POSITION, lastPosition);
        outState.putParcelable(LAST_RECIPE, mRecipe);
    }
}
