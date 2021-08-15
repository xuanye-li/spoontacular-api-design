package cmu.edu.andrew.spoonacular;

import java.util.*;

/**
 * A searcher object used to find recipes from Spoonacular.
 * Is used to find all the different recipes that meet a search criteria. To use the {@code ReciperSearcher}, users
 * need a spoonacular API key from https://spoonacular.com/food-api.
 * <p>
 * To create an instance of a {@code RecipeSearcher}, first create an instance of {@code ReciperSearcher.Builder}. 
 * Then, set the search criteria that you would like the RecipeSearcher to have. Finally, invoking
 * the {@code build()} method of {@code RecipeSearcher.Builder} will create a {@code RecipeSearcher} instance.
 * After an instance is created, users can use {@code search()} to get a list of recipes from Spoonacular that meet
 * the search criteria. 
 * <p>
 * Search Criteria
 * Setting the search criteria can be divided into six groups of methods: name-related, ingredients-related, 
 * diet-related, intolerance-related, and price-related. Name-related methods are used to specify keywords in the
 * recipe name that should be included. Ingredients-related methods are used to specify ingredients that should be 
 * included in the recipe. Diet-related methods are used to search for recipes that can meet a specific diet. 
 * Cuisine-related methods are used to specify cuisines the recipe should belong to. Nutrient-related
 * methods are used to specify ranges that one serving of the recipe should meet. Price-related methods are used to
 * specify a minimum and maximum price for each serving of the recipe.
 * <p>
 * Sample Code
 * {@code 
 * RecipeSearcher searcher = new RecipeSearcher.Builder()<br>
 * .addIngredient("beef") .addDiets(Diet.KETOGENIC) .setLowestPrice(0) .setHighestPrice(100)<br>
 * .build();<br>
 * 
 * List&lt;Recipe&gt; myBeefRecipes = searcher.search();<br>
 * }
 */
public class RecipeSearcher {
    private Map<String, String> parameters = new HashMap<String, String>();
    private int minPrice, maxPrice;

    /**
     * Returns a list of {@code Recipe}s from Spoonacular that meets this {@code RecipeSearcher}'s search criteria.
     * @param apiKey The spoonacular API key to be used to make this call.
     * @return a list of {@code Recipe}s from Spoonacular that meets this {@code RecipeSearcher}'s search criteria.
     */
    public List<Recipe> search(String apiKey) {
         List<Recipe> recipes = RestfulApiHelper.getRecipes(parameters, apiKey);

         if(minPrice != Integer.MIN_VALUE || maxPrice != Integer.MAX_VALUE) {
             List<Recipe> outOfRange = new ArrayList<Recipe>();
             for(Recipe r : recipes) {
                 if(r.pricePerServingInCents() < minPrice || r.pricePerServingInCents() > maxPrice) {
                    outOfRange.add(r);
                 }
             }
             recipes.removeAll(outOfRange);
         }

         return recipes;
    }

    /**
     * Builder class for RecipeSearcher.
     * Contains methods to specify the search criteria.
     * Setting the search criteria can be divided into six groups of methods: name-related, ingredients-related, 
     * diet-related, intolerance-related, and price-related. Name-related methods are used to specify keywords in the
     * recipe name that should be included. Ingredients-related methods are used to specify ingredients that should be 
     * included in the recipe. Diet-related methods are used to search for recipes that can meet a specific diet. 
     * Cuisine-related methods are used to specify cuisines the recipe should belong to. Nutrient-related
     * methods are used to specify ranges that one serving of the recipe should meet. Price-related methods are used to
     * specify a minimum and maximum price for each serving of the recipe.
     */
    public static class Builder {
        private String name;
        private Set<String> ingredients;
        private Set<String> excludedIngredients;
        private Diet diet;
        private Set<Intolerant> intolerances;
        private Set<Cuisine> cuisines;
        private Set<Cuisine> excludedCuisines;
        private Map<Nutrient, Amount> nutrientMins;
        private Map<Nutrient, Amount> nutrientMaxs;
        private int minPrice;
        private int maxPrice;
        
        /** 
         * Constructs a builder with default search criteria.
         * The default search criteria is: 
         * <ul>
         *      <li>no ingredients to include</li>
         *      <li>no diets to meet</li>
         *      <li>no cuisines to include</li>
         *      <li>no intolerances to avoid</li>
         *      <li>no price range</li>
         * </ul>
         */
        public Builder() {
            name = "";
            ingredients = new HashSet<String>();
            excludedIngredients = new HashSet<String>();
            diet = null;
            intolerances = new HashSet<Intolerant>();
            cuisines = new HashSet<Cuisine>();
            excludedCuisines = new HashSet<Cuisine>();
            nutrientMins = new HashMap<Nutrient, Amount>();
            nutrientMaxs = new HashMap<Nutrient, Amount>();
            minPrice = Integer.MIN_VALUE;
            maxPrice = Integer.MAX_VALUE;
        }
        
        /**
         * Builds a {@code RecipeSearcher} with the search criteria specified by this {@code Builder}.
         * @return a {@code RecipeSearcher} with the search criteria specified by this {@code Builder}.
         */
        public RecipeSearcher build() {
            RecipeSearcher searcher = new RecipeSearcher();

            if (name.length() > 0) {
                searcher.parameters.put("query", name);
            }

            if (!cuisines.isEmpty()) {
                String cuisineList = "";
                for (Cuisine c : cuisines)
                    cuisineList += c.toString() + ",";
                cuisineList = cuisineList.substring(0, cuisineList.length() - 1);
                searcher.parameters.put("cuisine", cuisineList);
            }

            if (!excludedCuisines.isEmpty()) {
                String excludedCuisineList = "";
                for (Cuisine c : excludedCuisines)
                    excludedCuisineList += c.toString() + ",";
                excludedCuisineList = excludedCuisineList.substring(0, excludedCuisineList.length() - 1);
                searcher.parameters.put("excludeCuisine", excludedCuisineList);
            }

            if (diet != null) {
                searcher.parameters.put("diet", diet.toString());
            }

            if (!intolerances.isEmpty()) {
                String intolerantList = "";
                for (Intolerant i : intolerances)
                    intolerantList += i.toString() + ",";
                intolerantList = intolerantList.substring(0, intolerantList.length() - 1);
                searcher.parameters.put("intolerances", intolerantList);
            }

            if (!ingredients.isEmpty()) {
                String ingredientList = "";
                for (String i : ingredients)
                    ingredientList += i + ",";
                ingredientList = ingredientList.substring(0, ingredientList.length() - 1);
                searcher.parameters.put("includeIngredients", ingredientList);
            }

            if (!excludedIngredients.isEmpty()) {
                String excludedIngredientList = "";
                for (String i : excludedIngredients)
                    excludedIngredientList += i + ",";
                excludedIngredientList = excludedIngredientList.substring(0, excludedIngredientList.length() - 1);
                searcher.parameters.put("excludeIngredients", excludedIngredientList);
            }


            for (Nutrient n : nutrientMins.keySet()) {
                String name = n.name().replace(" ", "");
                Amount a = nutrientMins.get(n).convertUnit(n.defaultUnit());
                searcher.parameters.put("min" + name, Integer.toString((int) a.quantity()));
            }
            for (Nutrient n : nutrientMaxs.keySet()) {
                String name = n.name().replace(" ", "");
                Amount a = nutrientMaxs.get(n).convertUnit(n.defaultUnit());
                searcher.parameters.put("max" + name, Integer.toString((int) a.quantity()));
            }

            searcher.parameters.put("addRecipeInformation", "true");
            searcher.parameters.put("addRecipeNutrition", "true");

            searcher.minPrice = minPrice;
            searcher.maxPrice = maxPrice;

            return searcher;
        }

        /*************** Recipe name related functions **************/

        /**
         * Set the recipe name to search.
         * @param name the name to be searched.
         * @return builder with the new search criteria.
         */
        public Builder setRecipeName(String name) {
            this.name = name;
            return this;
        }
        
        /**
         * Removes the recipe name set for the recipe search. 
         * No specific recipe name will be part of the search criteria.
         * @return builder with the new search criteria.
         */
        public Builder removeRecipeName() {
            name = "";
            return this;
        }

        /*************** Ingredients related functions **************/

        /**
         * Adds an ingredient to the search criteria that recipes must include.
         * @param ingredient the ingredient to be added.
         * @return builder with the new search criteria.
         */
        public Builder addIngredient(String ingredient) {
            ingredients.add(ingredient);
            return this;
        }
        
        /**
         * Remove a previously added ingredient that was included to the search criteria.
         * @param ingredient the ingredient that was included but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeIngredient(String ingredient) {
            ingredients.remove(ingredient);
            return this;
        }

        /**
         * Adds a list of ingredients to the search criteria that recipes must include.
         * @param ingredients the ingredients to be added.
         * @return builder with the new search criteria.
         */
        public Builder addIngredients(List<String> ingredients) {
            this.ingredients.addAll(ingredients);
            return this;
        }

        /**
         * Removes previously added ingredients that were included to the search criteria.
         * @param ingredients the ingredients that were included but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeIngredients(List<String> ingredients) {
            this.ingredients.removeAll(ingredients);
            return this;
        }

        /**
         * Adds an ingredient to the search criteria that recipes must exclude.
         * @param ingredient the ingredient to be added.
         * @return builder with the new search criteria.
         */
        public Builder addExcludedIngredient(String ingredient) {
            excludedIngredients.add(ingredient);
            return this;
        }
        
        /**
         * Remove a previously added ingredient that was excluded to the search criteria.
         * @param ingredient the ingredient that was excluded but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeExcludedIngredient(String ingredient) {
            excludedIngredients.remove(ingredient);
            return this;
        }

        /**
         * Adds a list of ingredients to the search criteria that recipes must exclude.
         * @param ingredients the ingredients to be added for exclusion.
         * @return builder with the new search criteria.
         */
        public Builder addExcludedIngredients(List<String> ingredients) {
            this.excludedIngredients.addAll(ingredients);
            return this;
        }

        /**
         * Removes previously added ingredients that were excluded to the search criteria.
         * @param ingredients the ingredients that were excluded but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeExcludedIngredients(List<String> ingredients) {
            this.excludedIngredients.removeAll(ingredients);
            return this;
        }

        /*************** Diet related functions **************/

        /**
         * Set the diet that is used in the search criteria.
         * @param diet The diet that will be set to.
         * @return builder with the new search criteria.
         */
        public Builder setDiet(Diet diet) {
            this.diet = diet;
            return this;
        }
      
        /**
         * Removes a previously set diet from the search criteria. No specific diet will
         * be included after this call.
         * @return builder with the new search criteria.
         */
        public Builder removeDiet() {
            diet = null;
            return this;
        }

        /*************** Intolerance related functions **************/

        /**
         * Adds an intolerance that should be excluded from the search. 
         * Recipes that have the specified intolerance will not appear in the search.
         * @param intolerance the intolerance to be exluded from the search.
         * @return builder with the new search criteria.
         */
        public Builder addIntolerance(Intolerant intolerance) {
            intolerances.add(intolerance);
            return this;
        }

        /**
         * Removes a previously added intolerance that should be excluded from the search. 
         * Recipes that have the specified intolerance will not appear in the search.
         * @param intolerance the intolerance that was prevously added but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeIntolerance(Intolerant intolerance) {
            intolerances.remove(intolerance);
            return this;
        }

        /**
         * Adds a list of intolerances that should be excluded from the search. 
         * Recipes that have any of the specified intolerances will not appear in the search.
         * @param intolerances the intolerances to be exluded from the search.
         * @return builder with the new search criteria.
         */
        public Builder addIntolerances(List<Intolerant> intolerances) {
            this.intolerances.addAll(intolerances);
            return this;
        }

        /**
         * Removes a list of previously added intolerance that should be excluded from the search. 
         * Recipes that have the specified intolerance will not appear in the search.
         * @param intolerances the intolerances that were prevously added but will be removed.
         * @return builder with the new search criteria.
         */
        public Builder removeIntolerances(List<Intolerant> intolerances) {
            this.intolerances.removeAll(intolerances);
            return this;
        }

        /*************** Cuisine related functions **************/

        /**
         * Adds a cuisine to the search criteria that recipes must belong to.
         * Adding more than one cuisine will search for recipes that belong to any of the specified cuisines.
         * @param cuisine the cuisine to add.
         * @return builder with the new search criteria.
         */
        public Builder addCuisine(Cuisine cuisine) {
            cuisines.add(cuisine);
            return this;
        }

        /**
         * Removes a cuisine that was previously added to the search criteria.
         * @param cuisine the cuisine to remove.
         * @return builder with the new search criteria.
         */
        public Builder removeCuisine(Cuisine cuisine) {
            cuisines.remove(cuisine);
            return this;
        }

        /**
         * Adds a list of cuisines to the search criteria that recipes must belong to.
         * Adding more than one cuisine will search for recipes that belong to any of the specified cuisines.
         * @param cuisines the cuisines to add.
         * @return builder with the new search criteria.
         */
        public Builder addCuisines(List<Cuisine> cuisines) {
            this.cuisines.addAll(cuisines);
            return this;
        }


        /**
         * Removes cuisines that were previously added to the search criteria.
         * @param cuisines the cuisines to remove.
         * @return builder with the new search criteria.
         */
        public Builder removeCuisines(List<Cuisine> cuisines) {
            this.cuisines.removeAll(cuisines);
            return this;
        }

         /**
         * Adds a cuisine to the search criteria that recipes must not belong to.
         * Excluding more than one cuisine will search for recipes that belong to none of the specified cuisines.
         * @param cuisine the cuisine to be excluded.
         * @return builder with the new search criteria.
         */
        public Builder addExcludedCuisine(Cuisine cuisine) {
            excludedCuisines.add(cuisine);
            return this;
        }

        /**
         * Removes a cuisine that was previously excluded from the search criteria.
         * @param cuisine the excluded cuisine to remove.
         * @return builder with the new search criteria.
         */
        public Builder removeExcludedCuisine(Cuisine cuisine) {
            excludedCuisines.remove(cuisine);
            return this;
        }

        /**
         * Adds a list of cuisines to the search criteria that recipes must not belong to.
         * Adding more than one cuisine will search for recipes that belong to none of the specified cuisines.
         * @param cuisines the cuisines to be excluded.
         * @return builder with the new search criteria.
         */
        public Builder addExcludedCuisines(List<Cuisine> cuisines) {
            this.excludedCuisines.addAll(cuisines);
            return this;
        }


        /**
         * Removes cuisines that were previously excluded from the search criteria.
         * @param cuisines the excluded cuisines to remove.
         * @return builder with the new search criteria.
         */
        public Builder removeExcludedCuisines(List<Cuisine> cuisines) {
            this.excludedCuisines.removeAll(cuisines);
            return this;
        }

        /*************** Nutrients related functions **************/

        /**
         * Adds a minimum range restriction to the search criteria for a given nutrient.
         * @param nutrient the nutrient to be restricted.
         * @param quantity The minimum quantity of this nutrient the recipe must have.
         * @param units the units associated with the quantity.
         * @return builder with the new search criteria.
         */
        public Builder addNutrientMinimum(Nutrient nutrient, double quantity, Amount.Unit units) {
            nutrientMins.put(nutrient, new Amount(quantity, units));
            return this;
        }
        
        /**
         * Adds a maximum range restriction to the search criteria for a given nutrient.
         * @param nutrient the nutrient to be restricted.
         * @param quantity The maximum quantity of this nutrient the recipe can have.
         * @param units the units associated with the quantity.
         * @return builder with the new search criteria.
         */
        public Builder addNutrientMaximum(Nutrient nutrient, double quantity, Amount.Unit units) {
            nutrientMaxs.put(nutrient, new Amount(quantity, units));
            return this;
        }

        /**
         * Removes all nutritional range (minimum or maximum) restrictions for a given nutrient.
         * @param nutrient the nutrient to have the restrictions removed.
         * @return builder with the new search criteria.
         */
        public Builder removeNutrientLimits(Nutrient nutrient) {
            nutrientMins.remove(nutrient);
            nutrientMaxs.remove(nutrient);
            return this;
        }

        /**
         * Adds a minimum range restriction to the search criteria for every nutrient in nutrients.
         * @param nutrients the nutrients to be restricted. The key is the nutrient while the value is the minimum
         * restriction.
         * @param unit The unit that is associated with the values used in the map.
         * @return builder with the new search criteria.
         */
        public Builder addNutrientMinimums(Map<Nutrient, Double> nutrients, Amount.Unit unit) {
            for (Nutrient n : nutrients.keySet())
                addNutrientMinimum(n, nutrients.get(n), unit);
            return this;
        }

        /**
         * Adds a maximum range restriction to the search criteria for every nutrient in nutrients.
         * @param nutrients the nutrients to be restricted. The key is the nutrient while the value is the maximum
         * restriction.
         * @param unit The unit that is associated with the values used in the map.
         * @return builder with the new search criteria.
         */
        public Builder addNutrientMaximums(Map<Nutrient, Double> nutrients, Amount.Unit unit) {
            for (Nutrient n : nutrients.keySet())
                addNutrientMinimum(n, nutrients.get(n), unit);
            return this;
        }

        /**
         * Removes all the nutritional range restrictions from the search criteria for every nutrient in nutrients.
         * @param nutrients the nutrients where previously set range restrictions should be removed. Nutrients not 
         * included in this parameter will still have previously set range restrictions.
         * @return builder with the new search criteria.
         */
        public Builder removeNutrientLimits(List<Nutrient> nutrients) {
            for (Nutrient n : nutrients)
                removeNutrientLimits(n);
            return this;
        }

        /*************** Price Range related functions **************/

        /**
         * Sets the minimum price that the search criteria will include.
         * @param price the lowest price that the search criteria will include.
         * @return builder with the new search criteria.
         */
        public Builder setMinimumPrice(int price) {
            minPrice = price;
            return this;
        }

        /**
         * Sets the maximum price that the search criteria will include.
         * @param price the highest price that the search criteria will include.\
         * @return builder with the new search criteria.
         */
        public Builder setMaximumPrice(int price) {
            maxPrice = price;
            return this;
        }

        /**
         * Removes all price range (lowest or highest) restrictions from the search criteria.
         * @return builder with the new search criteria.
         */
        public Builder removePriceLimits() {
            minPrice = Integer.MIN_VALUE;
            maxPrice = Integer.MAX_VALUE;
            return this;
        }
    }
}
