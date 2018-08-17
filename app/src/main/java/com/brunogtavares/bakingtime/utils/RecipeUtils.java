package com.brunogtavares.bakingtime.utils;

import com.brunogtavares.bakingtime.model.Ingredient;
import com.brunogtavares.bakingtime.model.Recipe;
import com.brunogtavares.bakingtime.model.Step;

/**
 * Created by brunogtavares on 8/9/18.
 */

public final class RecipeUtils {

    private RecipeUtils(){};

    public static String convertQuantity(Double qty) {
        return qty % 1 == 0 ? String.valueOf(qty.intValue()) : String.valueOf(qty);
    }

    public static String convertMeasure(String measure, double quantity) {

        switch(measure) {
            case "CUP":
                return quantity > 1 ? " cups of " : " cup of ";
            case "TBLSP":
                return " tblsp of ";
            case "TSP":
                return " tsp of ";
            case "K":
                return "kg of ";
            case "G":
                return "g of ";
            case "OZ":
                return "oz of ";
            case "UNIT":
                return " ";
            default:
                return " ";
        }
    }

    public static String getIngredientString(Ingredient ingredient) {

        double quantity = ingredient.getQuantity();

        String qty = convertQuantity(quantity);
        String measure = convertMeasure(ingredient.getMeasure(), quantity);
        String ingredientString = ingredient.getIngredient();

        return "• " + qty + measure + ingredientString;

    }

    public static String getStepString(Step step) {

        return step.getId() == 0 ? step.getShortDescription() :
                step.getId() + ". " + step.getShortDescription();
    }

}
