package com.brunogtavares.bakingtime;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.brunogtavares.bakingtime.RecipeDetailActivity.RECIPE_BUNDLE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailListFragment extends Fragment implements StepAdapter.StepsAdapterOnClickHandler {

    private Recipe mRecipe;
    private List<Step> mStepList;
    private List<Object> mRecipeDetailLists;
    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;
    private RecipeDetailViewModel mModel;

    public RecipeDetailListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_list, container, false);

        TextView ingredientTab = (TextView) rootView.findViewById(R.id.tv_ingredients_item);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(RECIPE_BUNDLE_KEY)) {
            mRecipe = intent.getParcelableExtra(RECIPE_BUNDLE_KEY);
            Timber.i("RECIPE INFO: " + mRecipe.toString());

            // Initializes the array with Object to add ingredients
            // and steps list.
            mRecipeDetailLists = new ArrayList<>();
            mRecipeDetailLists.add(mRecipe.getIngredients());
            mRecipeDetailLists.add(mRecipe.getSteps());

            // TODO set the this to clickListener
            ingredientTab.setText(R.string.ingredients_label);


            mRecyclerView = rootView.findViewById(R.id.rv_steps_list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mStepAdapter = new StepAdapter(this);
            mStepAdapter.setStepList(new ArrayList<Step>());
            mRecyclerView.setAdapter(mStepAdapter);

            initViewModel();
        }
        return rootView;
    }



    private void initViewModel() {
        mModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        mModel.setRecipe(mRecipe);
        mStepList = mModel.getStepList();
        Timber.i("STEP LIST SIZE: " + mStepList.size());

        mStepAdapter.setStepList(mStepList);

    }


    @Override
    public void onClick(Step step) {
        mModel.selectStep(step);
    }
}
