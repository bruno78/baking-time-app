package com.brunogtavares.bakingtime;

import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by brunogtavares on 8/17/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource mIdlingResource;

    @Rule public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnRecipe_TakesToDetailRecipeWithSteps() {
        // 1. find the view

        // 2. Perform action on the view

        // 3. check if the view does what it's expected
    }
}
