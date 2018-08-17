package com.brunogtavares.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogtavares on 8/17/18.
 */

public class MockRecipeData implements Parcelable {

    private Recipe recipe;
    private Ingredient ingredient;
    private Step step;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public MockRecipeData(){}

    public Recipe getMockNutellaPie() {
        recipe = new Recipe(0, "Nutella Pie",
                getMockNutellaPieIngredients(), getMockNutellaPieSteps(), 8, "");
        return recipe;
    }

    private List<Ingredient> getMockNutellaPieIngredients() {
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(2, "CUP", "Graham Cracker crumbs"));
        ingredients.add(new Ingredient(6, "TBLSP", "unsalted butter, melted" ));
        ingredients.add(new Ingredient(0.5, "CUP", "granulated sugar"));

        return ingredients;
    }

    private List<Step> getMockNutellaPieSteps() {
        steps = new ArrayList<>();
        steps.add( new Step(0,"Recipe Introduction",  "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", "") );
        steps.add( new Step(1, "Starting prep", "1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.", "","" ));
        steps.add( new Step(5, "Finish filling prep", "5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.", "",  "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4" ));
        steps.add( new Step(6, "Finishing Steps", "6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4", ""));

        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.recipe, flags);
        dest.writeParcelable(this.ingredient, flags);
        dest.writeParcelable(this.step, flags);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }

    private MockRecipeData(Parcel in) {
        this.recipe = in.readParcelable(Recipe.class.getClassLoader());
        this.ingredient = in.readParcelable(Ingredient.class.getClassLoader());
        this.step = in.readParcelable(Step.class.getClassLoader());
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<MockRecipeData> CREATOR = new Parcelable.Creator<MockRecipeData>() {
        @Override
        public MockRecipeData createFromParcel(Parcel source) {
            return new MockRecipeData(source);
        }

        @Override
        public MockRecipeData[] newArray(int size) {
            return new MockRecipeData[size];
        }
    };

}
