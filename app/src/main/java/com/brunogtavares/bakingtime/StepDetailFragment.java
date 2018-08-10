package com.brunogtavares.bakingtime;


import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {

    private static final String SAVED_STEP = "SAVED_STEP";
    private static final String EXO_PLAYER_LAST_POSITION = "EXO_PLAYER_LAST_POSITION";
    private static final String EXO_PLAYER_CURRENT_WINDOW = "EXO_PLAYER_CURRENT_WINDOW";
    private static final String PLAY_WHEN_READY = "PLAY_WHEN_READY";

    @BindView(R.id.ep_exoplayer_view) PlayerView mPlayerView;
    @BindView(R.id.rl_no_video_holder) RelativeLayout mNoVideoImageHolder;
    @BindView(R.id.tv_step_description) TextView mStepDescription;
    @BindView(R.id.tv_step_short_description) TextView mStepShortDescription;

    Step mStep;
    RecipeDetailViewModel mModel;
    SimpleExoPlayer mExoPlayer;

    // ExoPlayer setup variables
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow = 0;
    private long mPlaybackPosition = 0;
    Uri mUri;

    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        ButterKnife.bind(this, rootView);

        Bundle bundle = new Bundle();
        if(bundle.containsKey(PLAY_WHEN_READY)) {
            mPlayWhenReady = bundle.getBoolean(PLAY_WHEN_READY);
        }

        if(savedInstanceState == null) {
            mModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
            mStep = mModel.getSelected();
        }
        else {
            mStep = savedInstanceState.getParcelable(SAVED_STEP);
            mPlaybackPosition = savedInstanceState.getLong(EXO_PLAYER_LAST_POSITION);
            mCurrentWindow = savedInstanceState.getInt(EXO_PLAYER_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        initializePlayer();

        populateUI();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(EXO_PLAYER_LAST_POSITION, mExoPlayer.getCurrentPosition());
        outState.putInt(EXO_PLAYER_CURRENT_WINDOW, mExoPlayer.getCurrentWindowIndex());
        outState.putBoolean(PLAY_WHEN_READY, mPlayWhenReady);
        outState.putParcelable(SAVED_STEP, mStep);
    }

    private void populateUI() {

        String shortDescriptionString = mStep.getShortDescription();
        String descriptionString = mStep.getDescription();
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

        mStepShortDescription.setText(shortDescriptionString);
        mStepDescription.setText(descriptionString);

    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

            if(mUri != null) {
                MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("ExoPlayer"))
                        .createMediaSource(mUri);
                mExoPlayer.prepare(mediaSource, false, false);
            }
        }
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23) {
           initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Util.SDK_INT <= 23 || mExoPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


}
