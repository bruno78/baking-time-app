package com.brunogtavares.bakingtime;


import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brunogtavares.bakingtime.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.brunogtavares.bakingtime.RecipeDetailActivity.SAVED_STEP;


/**
 * A simple {@link Fragment} subclass.
 * This Fragment displays details of the step.
 * 1. Title, video if available, and Description
 */
public class StepDetailFragment extends Fragment {

    private static final String LAST_POSITION = "LAST_POSITION";
    private static final String LAST_CURRENT_WINDOW = "LAST_CURRENT_WINDOW";
    private static final String PLAY_WHEN_READY = "PLAY_WHEN_READY";

    // Exoplayer
    private SimpleExoPlayer mPlayer;
    private long mPlaybackPosition = 0;
    private int mCurrentWindow = 0;
    private boolean mPlayWhenReady = true;

    // Step
    private Step mStep;
    private RecipeDetailViewModel mModel;
    private Uri mUri;
    private int mStepId;
    private int mNumberOfSteps;

    @BindView(R.id.ep_exoplayer_view) PlayerView mPlayerView;
    @BindView(R.id.rl_no_video_holder) RelativeLayout mNoVideoImageHolder;
    @BindView(R.id.tv_step_description) TextView mStepDescription;
    @BindView(R.id.tv_step_short_description) TextView mStepShortDescription;
    @BindView(R.id.bt_next_step) Button mNextButton;
    @BindView(R.id.bt_previous_step) Button mPrevButton;

    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        if(getActivity() != null) {
            mModel = ViewModelProviders.of(getActivity()).get(RecipeDetailViewModel.class);
        }
        if(savedInstanceState == null) {
            mStep = mModel.getSelected();
        }
        else {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
            mPlaybackPosition = savedInstanceState.getLong(LAST_POSITION);
            mCurrentWindow = savedInstanceState.getInt(LAST_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        mStepId = mStep.getId();
        mNumberOfSteps = mModel.getNumberOfSteps();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            populateVideoUI();
        }
        else {
            populateUI();
        }

        // Set next and prev buttons onClickListeners
        mNextButton.setOnClickListener(view -> {
            mStepId++;
            updateUI();
            initializePlayer();
        });
        mPrevButton.setOnClickListener(view -> {
            mStepId--;
            updateUI();
            initializePlayer();
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(SAVED_STEP, mStep);
    }

    // This private method populates the UI and set the next and prev buttons accordingly.
    private void populateUI() {

        if(mStepId < 1) {
            mPrevButton.setEnabled(false);
        }
        else {
            mPrevButton.setEnabled(true);
        }

        if(mStepId > mNumberOfSteps-2) {
            mNextButton.setEnabled(false);
        }
        else {
            mNextButton.setEnabled(true);
        }

        String shortDescriptionString = mStep.getShortDescription();
        if (mStep.getId() != 0)
            shortDescriptionString = mStep.getId() + ". " + mStep.getShortDescription();

        String descriptionString = mStep.getDescription();

        populateVideoUI();

        mStepShortDescription.setText(shortDescriptionString);
        mStepDescription.setText(descriptionString);

    }

    private void populateVideoUI() {
        String videoUrlString = mStep.getVideoUrl();

        if (TextUtils.isEmpty(videoUrlString)) {
            mNoVideoImageHolder.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        }
        else {
            mUri = Uri.parse(videoUrlString);
            mPlayerView.setVisibility(View.VISIBLE);
            mNoVideoImageHolder.setVisibility(View.GONE);
        }
    }


    private void updateUI() {
        mStep = mModel.getStep(mStepId);
        populateUI();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
        }
    }

    // Initialize ExoPlayer
    private void initializePlayer() {

        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
        }
        mPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(mPlayWhenReady);
        mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

        if(mUri != null) {
            MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory("ExoPlayer"))
                    .createMediaSource(mUri);
            mPlayer.prepare(mediaSource, false, false);
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    // This is called onResume is just an implementation detail to have
    // a pure screen navigation.
    private void hideSystemUI() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
        }
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
