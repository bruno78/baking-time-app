package com.brunogtavares.bakingtime;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brunogtavares.bakingtime.model.Step;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.brunogtavares.bakingtime.RecipeDetailActivity.SAVED_STEP;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {

    private Step mStep;
    private RecipeDetailViewModel mModel;
    private Uri mUri;

    @BindView(R.id.ep_exoplayer_view) PlayerView mPlayerView;
    @BindView(R.id.rl_no_video_holder) RelativeLayout mNoVideoImageHolder;
    @BindView(R.id.tv_step_description) TextView mStepDescription;
    @BindView(R.id.tv_step_short_description) TextView mStepShortDescription;


    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null) {

            mModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
            mStep = mModel.getSelected();
        }
        else {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
        }

        populateUI();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVED_STEP, mStep);
    }

    private void populateUI() {

        // Timber.i("Receiving from Ingredient Step Fragment: " + mStep.getShortDescription());

        String shortDescriptionString = mStep.getShortDescription();
        String descriptionString = mStep.getDescription();
        // String videoUrlString = mStep.getVideoUrl();
        String videoUrlString = "";

        if (TextUtils.isEmpty(videoUrlString)) {
            mNoVideoImageHolder.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        }
        else {
            mUri = Uri.parse(videoUrlString);
            mPlayerView.setVisibility(View.VISIBLE);
            mNoVideoImageHolder.setVisibility(View.GONE);

        }

        mStepShortDescription.setText(shortDescriptionString);
        mStepDescription.setText(descriptionString);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
        }
    }
}
