package cmu.edu.andrew.sample.code;

import cmu.edu.andrew.spoonacular.*;
import java.util.*;

public class ClientCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Spoonacular API key: ");
        String key = scanner.next();

        // USE CASE: search by recipe name
        RecipeSearcher searcher0 = (new RecipeSearcher.Builder()).setRecipeName("spaghetti bolognese").build();
        List<Recipe> myRecipes0 = searcher0.search(key);
        for (Recipe r: myRecipes0) {
            System.out.println(r.toString());
        }

        // USE CASE: general use case
        RecipeSearcher searcher1 = new RecipeSearcher.Builder().addIngredient("chicken").addCuisine(Cuisine.CHINESE).setMaximumPrice(1000).build();
        List<Recipe> myRecipes1 = searcher1.search(key);

        if (!myRecipes1.isEmpty()) {
            Recipe r = myRecipes1.get(0);
            Map<Ingredient, Amount> ingredients = r.ingredients();
            Map<Nutrient, Amount> nutrition = r.nutrients();
            List<Step> instructions = r.instructions(); // rename instructions to steps

            System.out.println("ingredients");
            for (Map.Entry<Ingredient, Amount> entry: ingredients.entrySet()) {
                System.out.println(entry.getValue().toString() + " of " + entry.getKey().toString());
            }

            System.out.println("Instructions: ");
            for (Step s: instructions) {
                System.out.println(s.toString());
            }
        }
        else {
            System.out.println("no recipes found");
        }

    }
}