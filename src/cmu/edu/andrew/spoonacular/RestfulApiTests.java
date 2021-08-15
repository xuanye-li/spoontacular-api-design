package cmu.edu.andrew.spoonacular;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
//import cmu.edu.andrew.spoonacular.RestfulApiHelper;

public class RestfulApiTests {
    // Please don't abuse my api key. It's linked to my credit card :(
    private final String adrianApiKey = "please fill in your own api key";

    @Test
    public void getRecipesFullInfoTest() {

        // Search for ingredient banana chips
        Map<String, String> map = new HashMap<>();
        map.put("number", "1");
        map.put("addRecipeNutrition", "true");
        List<Recipe> recipes = RestfulApiHelper.getRecipes(map, adrianApiKey);
        Assert.assertEquals(recipes.size(), 1);
        for (Recipe recipe : recipes) {
            // Test recipe name
            Assert.assertNotEquals(recipe.name(), null);
            Assert.assertEquals(recipe.name(), "Cauliflower, Brown Rice, and Vegetable Fried Rice");

            // Test source url
            Assert.assertNotEquals(recipe.sourceURL(), null);
            Assert.assertEquals(recipe.sourceURL(), "http://fullbellysisters.blogspot.com/2012/01/cauliflower-fried-rice-more-veggies.html");

            // Test image url
            Assert.assertNotEquals(recipe.imageURL(), null);
            Assert.assertEquals(recipe.imageURL(), "https://spoonacular.com/recipeImages/716426-312x231.jpg");

            // Test recipe id
            Assert.assertEquals(recipe.id(), 716426);

            // Test prep time
            Assert.assertEquals(recipe.prepTimeInMinutes(), 30);

            // Test serving
            Assert.assertEquals(recipe.servings(), 8);

            // Test price
            Assert.assertEquals(recipe.pricePerServingInCents(), 112);

            // Test health score
            Assert.assertEquals(recipe.healthScore(), 76);

            // Test spoonacular score
            Assert.assertEquals(recipe.spoonacularScore(), 99);

            // Test cuisines. It should have ASIAN but our cuisine.fromString function doesn't support that right now
            Assert.assertNotEquals(recipe.cuisines(), null);
            Assert.assertEquals(recipe.cuisines().size(), 1);
            Assert.assertEquals(recipe.cuisines().get(0), Cuisine.CHINESE);

            // Test diets. It should have dairy free and lacto ovo vegetarian but we are not supporting rn
            Assert.assertNotEquals(recipe.allowableDiets(), null);
            Assert.assertEquals(recipe.allowableDiets().size(), 2);
            Assert.assertEquals(recipe.allowableDiets().get(0), Diet.GLUTEN_FREE);
            Assert.assertEquals(recipe.allowableDiets().get(1), Diet.VEGAN);

            // Test intolerant.
            Assert.assertNotEquals(recipe.intolerantsContained(), null);
            Assert.assertEquals(recipe.intolerantsContained().size(), 2);
            Assert.assertEquals(recipe.intolerantsContained().get(0), Intolerant.DAIRY);
            Assert.assertEquals(recipe.intolerantsContained().get(1), Intolerant.GLUTEN);

            // Test ingredients.
            Assert.assertNotEquals(recipe.ingredients(), null);
            Assert.assertEquals(recipe.ingredients().size(), 12);
            Map<Ingredient, Amount> ingredientMap = recipe.ingredients();
            for (Map.Entry<Ingredient, Amount> entry : ingredientMap.entrySet()) {
                // Check the broccoli ingredient class
                if (entry.getKey().name().equals("broccoli")) {
                    Ingredient broccoliIngredient = entry.getKey();
                    Amount broccoliAmount = entry.getValue();
                    Assert.assertTrue(Math.abs(broccoliAmount.quantity() - 0.25) < 0.01);
                    Assert.assertEquals(broccoliAmount.unit(), Amount.Unit.CUPS);
                }
            }

            // Test nutrients
            Assert.assertNotEquals(recipe.nutrients(), null);
            Assert.assertEquals(recipe.nutrients().size(), 28);
            Assert.assertEquals(recipe.nutrients().get(Nutrient.SUGAR).unit(), Amount.Unit.GRAMS);
            Assert.assertTrue(Math.abs(recipe.nutrients().get(Nutrient.SUGAR).quantity() - 3.32) < 0.01);

            // Test instruction
            Assert.assertNotEquals(recipe.instructions(), null);
            Assert.assertEquals(recipe.instructions().size(), 9);
            Assert.assertEquals(recipe.instructions().get(0).equipments().size(), 1);
            Assert.assertEquals(recipe.instructions().get(0).equipments().get(0).id(), 404771);
            Assert.assertEquals(recipe.instructions().get(0).equipments().get(0).Name(), "food processor");
            Assert.assertEquals(recipe.instructions().get(0).equipments().get(0).ImageURL(), "food-processor.png");
            Assert.assertEquals(recipe.instructions().get(0).toString(), "Remove the cauliflower's tough stem and reserve for another use. Using a food processor, pulse cauliflower florets until they resemble rice or couscous. You should end up with around four cups of \"cauliflower rice.\"");
            Assert.assertNotEquals(recipe.instructions().get(0).ingredients(), null);
            Assert.assertEquals(recipe.instructions().get(0).ingredients().get(0).name(), "cauliflower florets");
            Assert.assertEquals(recipe.instructions().get(0).ingredients().get(0).id(), 10011135);
        }
    }
}