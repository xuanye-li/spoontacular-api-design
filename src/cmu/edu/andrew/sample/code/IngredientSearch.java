package cmu.edu.andrew.sample.code;

import cmu.edu.andrew.spoonacular.*;
import java.util.*;

public class IngredientSearch {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Spoonacular API key: ");
        String key = s.next();

        RecipeSearcher.Builder searchBuilder = new RecipeSearcher.Builder();
        boolean done = false;
        while(!done) {
            System.out.print("Add an ingredient to search, or type Done to search: ");
            String ingredient = s.next();
            if(ingredient.equals("Done"))
                done = true;
            else
                searchBuilder.addIngredient(ingredient);
        }

        List<Recipe> recipes = searchBuilder.build().search(key);
        System.out.println(recipes.size() + " recipes found");
        for (Recipe r : recipes) {
            System.out.println();
            System.out.println(r.name() + "\n" + r.sourceURL());
        }
    }
}