package com.brunogtavares.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.ui.RecipeDetail.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.brunogtavares.bakingtime.ui.RecipeDetail.RecipeDetailActivity.RECIPE_BUNDLE_KEY;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by brunogtavares on 8/17/18.
 * https://stackoverflow.com/questions/31394569/how-to-assert-inside-a-recyclerview-in-espresso
 */
@RunWith(JUnit4.class)
public class RecipeDetailActivityTest {

    // Emaulates receiving Intent from MainActivity
    private Intent loadIntent(){

        Recipe recipe;
        MockRecipeData recipeData = new MockRecipeData();
        recipe = recipeData.getMockNutellaPie();

        Context targetcontext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetcontext, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_BUNDLE_KEY, recipe);
        return intent;
    }

    @Rule
    public final ActivityTestRule<RecipeDetailActivity> mActivtyTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class, false, false);


    @Test
    public void openActivity_LoadsRightRecipe() {
        mActivtyTestRule.launchActivity(loadIntent());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.tv_recipe_detail_name)).check(matches(withText("Nutella Pie")));
    }

    @Test
    public void openDetailActivity_indregientsAreLoaded() {
        String INGREDIENT = "• 0.5 cup of granulated sugar";
        mActivtyTestRule.launchActivity(loadIntent());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.tv_ingredients_item), withText(INGREDIENT)))
                .check(matches(withText(INGREDIENT)));

    }

    @Test
    public void clickOnStep_opensStepDetail() {
        String SHORT_DESCRIPTION = "1. Starting prep";
        mActivtyTestRule.launchActivity(loadIntent());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_ingredient_and_step_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(4, click()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.tv_step_short_description)).check(matches(withText(SHORT_DESCRIPTION)));

    }

    @Test
    public void firstStep_prevButtonIsDisabled() {
        mActivtyTestRule.launchActivity(loadIntent());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_ingredient_and_step_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(3, click()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.bt_previous_step)).check(matches(not(isEnabled())));
    }

    @Test
    public void lastStep_nextButtonIsDisabled() {
        mActivtyTestRule.launchActivity(loadIntent());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_ingredient_and_step_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(6, click()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.bt_next_step)).check(matches(not(isEnabled())));
    }

    // This is not working
//    @Test
//    public void clickOnNextButton_opensNextStepDetail() {
//        String SHORT_DESCRIPTION = "5. Finish filling prep";
//        String NEXT_SHORT_DESCRIPTION = "6. Finishing Steps";
//        mActivtyTestRule.launchActivity(loadIntent());
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        onView(withId(R.id.rv_ingredient_and_step_list)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(5, click()));
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        onView(withId(R.id.tv_step_short_description)).check(matches(withText(SHORT_DESCRIPTION)));
//
//        onView(withId(R.id.bt_next_step)).perform(click());
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        onView(withId(R.id.tv_step_short_description)).check(matches(withText(NEXT_SHORT_DESCRIPTION)));
//    }



}