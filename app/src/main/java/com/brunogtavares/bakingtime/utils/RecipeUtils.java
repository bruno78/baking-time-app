package com.brunogtavares.bakingtime.utils;

import com.brunogtavares.bakingtime.model.Recipe;

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

}
