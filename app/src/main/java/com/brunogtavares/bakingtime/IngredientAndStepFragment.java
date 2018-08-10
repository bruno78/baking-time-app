package com.brunogtavares.bakingtime;


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

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.brunogtavares.bakingtime.RecipeDetailActivity.RECIPE_BUNDLE_KEY;


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

    private SelectStep mSelectStep;

    public IngredientAndStepFragment() {
        // Required empty public constructor
    }

    public interface SelectStep {
        void stepSelected();
    }

    public void setSelectStep(SelectStep selectStep) {
        this.mSelectStep = selectStep;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSelectStep = (SelectStep) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredient_and_step, container, false);

        if(savedInstanceState == null) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
                mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
                Timber.i("RECIPE INFO: " + mRecipe.toString());
            }
        }
        else {
            mRecipe = savedInstanceState.getParcelable(LAST_RECIPE);
        }

        initViewModel();

        mRecyclerView = rootView.findViewById(R.id.rv_ingredient_and_step_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mIngredientAndStepAdapter = new IngredientAndStepAdapter(mIngredientAndStepList);
        mRecyclerView.setAdapter(mIngredientAndStepAdapter);

        if(savedInstanceState != null) {
            int position = savedInstanceState.getInt(LAST_POSITION);
            mRecyclerView.scrollToPosition(position);
        }

        return rootView;
    }

    private void initViewModel() {
        mModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        mModel.setRecipe(mRecipe);
        // Initializes the list with Object to add ingredients
        // and steps list.
        mIngredientAndStepList = mModel.getIngredientAndStepList();

        Timber.i("INGREDIENTS AND STEPS LIST SIZE: " + mIngredientAndStepList.size());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        // saves the position of the scrolling list.
        // Since the list only has 4 elements and they almost fit on the screen
        // it's not really necessary. It will be kept here in case for scalability
        // (in case the number of recipe available grows).
        int lastPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        outState.putInt(LAST_POSITION, lastPosition);
        outState.putParcelable(LAST_RECIPE, mRecipe);
    }

    @Override
    public void onClick(Step step) {
        mModel.selectStep(step);
        mSelectStep.stepSelected();;
    }
}
