package com.brunogtavares.bakingtime.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by brunogtavares on 8/17/18.
 * This class helps to test for features that requires asynchronous tasks.
 * Espresso waits until the app is idle to perform the next action. By idle,
 * it means:
 * 1. No UI events in the current message queue
 * 2. No more tasks in the default AsyncTask thread pool
 *
 * NOTE: Consider using CountingIdlingResource from espresso-contrib package if you use this class
 * from multiple trheads or need to keep a count of pending opearions.
 */
public class SimpleIdlingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mCallback;

    // Idleness is controlled with this boolean
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
