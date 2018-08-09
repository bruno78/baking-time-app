package com.brunogtavares.bakingtime;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
 */
public class IngredientAndStepFragment extends Fragment implements IngredientAndStepAdapter.OnStepClickHandler {

    private Recipe mRecipe;
    private List<Object> mIngredientAndStepList;
    private RecyclerView mRecyclerView;
    private IngredientAndStepAdapter mIngredientAndStepAdapter;
    private RecipeDetailViewModel mModel;

    public IngredientAndStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredient_and_step, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
            Timber.i("RECIPE INFO: " + mRecipe.toString());

            initViewModel();

            mRecyclerView = rootView.findViewById(R.id.rv_ingredient_and_step_list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mIngredientAndStepAdapter = new IngredientAndStepAdapter(mIngredientAndStepList);
            mRecyclerView.setAdapter(mIngredientAndStepAdapter);

        }
        return rootView;
    }

    private void initViewModel() {
        mModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        mModel.setRecipe(mRecipe);
        // Initializes the array with Object to add ingredients
        // and steps list.
        mIngredientAndStepList = mModel.getIngredientAndStepList();

        Timber.i("INGREDIENTS AND STEPS LIST SIZE: " + mIngredientAndStepList.size());
    }


    @Override
    public void onClick(Step step) {
        mModel.selectStep(step);
    }
}
