package com.brunogtavares.bakingtime;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by brunogtavares on 8/17/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource mIdlingResource;

    @Rule public final ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }

    @Test
    public void openApp_showRecipes() {
        // This test checks if Recycler view has at least 1 item
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.rv_recipe_list);
        int count = recyclerView.getAdapter().getItemCount();
        if (count > 0) {
            onView(withId(R.id.rv_recipe_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

    @Test
    public void clickOnRecipe_takesToRecipeDetail() {
        int POSITION = 2;
        String RECIPE_NAME = "Yellow Cake";

        // First, go to the position that needs to be matched
        // 1. find the view
        // 2. Perform action on the view
        onView(withId(R.id.rv_recipe_list))
                .perform( RecyclerViewActions.actionOnItemAtPosition(POSITION, click()));

        // 3. check if the view does what it's expected
        onView(withId(R.id.tv_recipe_detail_name)).check(matches(withText(RECIPE_NAME)));
    }

    @Test
    public void clickOnRecipeStep_takesToStepDetail() {
        int POSITION = 9;
        String STEP_NAME = "";
        onView(withId(R.id.rv_ingredient_and_step_list))
                .perform( RecyclerViewActions.actionOnItemAtPosition(POSITION, click()));
    }

}
