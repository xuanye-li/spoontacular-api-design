package cmu.edu.andrew.sample.code;

import cmu.edu.andrew.spoonacular.*;

import java.util.List;
import java.util.Scanner;

public class GreensAndEggs {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Spoonacular API key: ");
        String key = s.next();

        List<Recipe> recipes = (new RecipeSearcher.Builder()).setDiet(Diet.OVO_VEGETARIAN).build().search(key);
        System.out.println(recipes.size() + " recipes found");
        for (Recipe r : recipes) {
            System.out.println();
            System.out.println(r.name() + "\n" + r.sourceURL());
        }
    }
}
