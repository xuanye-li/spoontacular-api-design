package cmu.edu.andrew.spoonacular;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Parser {

    /**
     * Parse a JSON object into list of recipe object
     *
     * @param recipeJson
     * @return
     */
    static Recipe parseRecipe(JsonObject recipeJson) {
        JsonElement temp;
        // Parse id
        int id = parseInt(recipeJson, "id");

        // Parse ingredient name
        String ingredientName = parseString(recipeJson,"title");

        // Parse source url
        String sourceUrl = parseString(recipeJson,"sourceUrl");

        // Parse image url
        String imageUrl = parseString(recipeJson,"image");

        // Parse prepare time
        int prepTime = parseInt(recipeJson,"readyInMinutes");

        // Parse price
        int servings = parseInt(recipeJson,"servings");
        int pricePerServing = parseInt(recipeJson,"pricePerServing");

        // Parse health score
        int healthScore = parseInt(recipeJson,"healthScore");

        // Parse spoonacular score
        int spoonacularScore = parseInt(recipeJson,"spoonacularScore");

        // Parse cuisines
        List<Cuisine> cuisines = parseCuisine(recipeJson);

        // Parse diets
        List<Diet> diets = parseDiet(recipeJson);

        // Parse intolerant
        List<Intolerant> intolerants = parseIntolerant(recipeJson);

        // Parse nutrients and ingredients
        Map<Nutrient, Amount> nutritionMap = null;
        Map<Ingredient, Amount> ingredients = null;
        temp = recipeJson.get("nutrition");
        if (temp != null) {
            // Parse nutrition map
            nutritionMap = parseNutrition(temp.getAsJsonObject());

            // Parse ingredient list
            ingredients = parseIngredientsMap(temp.getAsJsonObject());
        }

        // Parse steps
        List<Step> steps = null;
        temp = recipeJson.get("analyzedInstructions");
        if (temp != null) {
            // Looks like there's always only one element in analyzedInstructions array
            for (JsonElement intruction : temp.getAsJsonArray()) {
                JsonElement stepsJson = intruction.getAsJsonObject().get("steps");
                if (stepsJson == null) {
                    break;
                }
                steps = new ArrayList<Step>();
                for (JsonElement stepJson: stepsJson.getAsJsonArray()) {
                    Step step = parseStep(stepJson.getAsJsonObject());
                    steps.add(step);
                }
            }
        }

        Recipe recipeBuilder = new Recipe.RecipeBuilder()
                .id(id)
                .name(ingredientName)
                .sourceUrl(sourceUrl)
                .imageUrl(imageUrl)
                .prepTime(prepTime)
                .servings(servings)
                .pricePerServing(pricePerServing)
                .healthScore(healthScore)
                .spoonacularScore(spoonacularScore)
                .cuisines(cuisines)
                .diets(diets)
                .intolerants(intolerants)
                .ingredients(ingredients)
                .nutrients(nutritionMap)
                .instructions(steps)
                .build();

        return recipeBuilder;
    }

    static List<Ingredient> parseIngredientsList(JsonObject ingredientsJson) {
        JsonElement temp = ingredientsJson.get("ingredients");
        if (temp == null) {
            return null;
        }
        List<Ingredient> ingredients =  new ArrayList<>();
        for (JsonElement ingredientJson : temp.getAsJsonArray()) {
            Ingredient ingredient = parseIngredient(ingredientJson.getAsJsonObject());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    static Map<Ingredient, Amount> parseIngredientsMap(JsonObject ingredientsJson) {
        JsonElement temp = ingredientsJson.get("ingredients");
        if (temp == null) {
            return null;
        }
        Map<Ingredient, Amount> map =  new HashMap<>();
        for (JsonElement ingredientJson : temp.getAsJsonArray()) {
            Ingredient ingredient = parseIngredient(ingredientJson.getAsJsonObject());
            Amount amount = parseAmount(ingredientJson.getAsJsonObject());
            map.put(ingredient, amount);
        }
        return map;
    }

    /**
     * Parse a JSON object to an ingredient object
     *
     * @param ingredientJson
     * @return
     */
    static Ingredient parseIngredient(JsonObject ingredientJson) {
        JsonElement temp;
        // Parse ingredient name
        String ingredientName = parseString(ingredientJson, "name");

        // Parse amount
        Amount amout = parseAmount(ingredientJson);

        // Parse price
        double price = -1.0;
        temp = ingredientJson.get("estimatedCost");
        if (temp != null) {
            price = parseDouble(temp.getAsJsonObject(), "value");
        }

        // Parse Nutrition
        Map<Nutrient, Amount> nutritionMap = null;
        temp = ingredientJson.get("nutrition");
        if (temp != null) {
            nutritionMap = parseNutrition(temp.getAsJsonObject());
        } else {
            // Try key word nutrient cuz recipe search is organized this way
            // In recipe search, it doesn't have the nutrition layer
            if (ingredientJson.get("nutrients") != null) {
                nutritionMap = parseNutrition(ingredientJson);
            }
        }

        // Parse ingredient id
        int id = parseInt(ingredientJson, "id");

        Ingredient ingredient = new Ingredient(ingredientName, id, (int)price, amout, nutritionMap);
        return ingredient;
    }

    static Map<Nutrient, Amount> parseNutrition(JsonObject json) {
        Map<Nutrient, Amount> nutrientMap = new HashMap<Nutrient, Amount>();
        JsonElement temp = json.getAsJsonObject().get("nutrients");
        if (temp == null) {
            return null;
        }
        int counter = 0;
        for (JsonElement jsonNutrient : temp.getAsJsonArray()) {
            counter++;
            JsonObject nutrientJsonObject = jsonNutrient.getAsJsonObject();
            // Parse nutrient name
            String nutrientString = parseString(nutrientJsonObject, "name");
            Amount nutrientAmount = parseAmount(nutrientJsonObject);
            // Skip this item if we can't form a complete Amount object
            if (nutrientString == null || nutrientAmount == null) {
                continue;
            }

            Nutrient nutrient;
            try{
                nutrient = Nutrient.fromString(nutrientString);
            }catch (Exception e){
                continue;
            }

            nutrientMap.put(nutrient, nutrientAmount);
        }
        return nutrientMap;
    }

    static Amount parseAmount(JsonObject json) {
        Amount amount = null;
        // Parse amount num
        double amountNum = parseDouble(json, "amount");

        // Parse amount unit
        String amountUnit = parseString(json, "unit");
        if (amountNum >= 0 && amountUnit != null) {
            // The amount fromString function won't throw exception
            amount = new Amount(amountNum, Amount.Unit.fromString(amountUnit));
        }
        return amount;
    }

    static Step parseStep(JsonObject json) {
        // Parse step name
        String instruction = parseString(json, "step");

        // Parse ingredients
        List<Ingredient> ingredients = parseIngredientsList(json);

        // Parse equipment
        List<Equipment> equipments = null;
        JsonElement equipmentsJson = json.get("equipment");
        if (equipmentsJson != null) {
            equipments = new ArrayList<Equipment>();
            for (JsonElement equipmentElement : equipmentsJson.getAsJsonArray()) {
                equipments.add(parseEquipment(equipmentElement.getAsJsonObject()));
            }
        }

        return new Step(instruction, ingredients, equipments);
    }

    static Equipment parseEquipment(JsonObject json) {
        int id = parseInt(json, "id");
        String imageUrl = parseString(json, "image");
        String name = parseString(json, "name");

        return new Equipment(id, name, imageUrl);
    }

    static List<Intolerant> parseIntolerant(JsonObject json) {
        JsonElement temp = json.get("dairyFree");
        List<Intolerant> intolerants = null;
        if (temp != null) {
            intolerants = new ArrayList<Intolerant>();
            if (temp.getAsBoolean()) {
                intolerants.add(Intolerant.DAIRY);
            }
        }
        temp = json.get("glutenFree");
        if (temp != null) {
            if (intolerants == null) {
                intolerants = new ArrayList<Intolerant>();
            }
            if (temp.getAsBoolean()) {
                intolerants.add(Intolerant.GLUTEN);
            }
        }
        return intolerants;
    }

    static List<Diet> parseDiet(JsonObject json) {
        List<Diet> diets = null;
        JsonElement dietsJson = json.get("diets");
        if (dietsJson != null) {
            diets = new ArrayList<Diet>();
            for (JsonElement dietJson : dietsJson.getAsJsonArray()) {
                Diet diet = null;
                try{
                    diet = Diet.fromString(dietJson.getAsString());
                }catch (Exception e) {
                    continue;
                }

                diets.add(diet);
            }
        }
        return diets;
    }

    static List<Cuisine> parseCuisine(JsonObject json) {
        List<Cuisine> cuisines = null;

        JsonElement cuisinesJson = json.get("cuisines");
        if (cuisinesJson != null) {
            cuisines = new ArrayList<Cuisine>();
            for (JsonElement cuisineJson : cuisinesJson.getAsJsonArray()) {
                Cuisine cuisine = null;
                try{
                    cuisine = Cuisine.fromString(cuisineJson.getAsString());
                }catch (Exception e) {
                    continue;
                }

                cuisines.add(cuisine);
            }
        }

        return cuisines;
    }

    static int parseInt(JsonObject json, String key) {
        JsonElement temp = json.get(key);
        if (temp != null) {
            return temp.getAsInt();
        }
        return -1;
    }

    static double parseDouble(JsonObject json, String key) {
        JsonElement temp = json.get(key);
        if (temp != null) {
            return temp.getAsDouble();
        }
        return -1.0;
    }

    static String parseString(JsonObject json, String key) {
        JsonElement temp = json.get(key);
        if (temp != null) {
            return temp.getAsString();
        }
        return null;
    }
}