package cmu.edu.andrew.spoonacular;

import java.util.*;

/**
 * A particular dish, with its preparation instructions, ingredients, nutritional info, and other metadata.
 * <p>
 * 
 * A {@code Recipe} cannot be constructed; it is returned by the API as the result of recipe searching.
 * <p>
 * The class contains 15 different accessors for all the various information about the {@code Recipe}:
 * <ul>
 *      <li>name(): name of the recipe.</li>
 *      <li>instructions(): preparation instructions.</li>
 *      <li>prepTimeInMinutes(): how long it takes to prepare the recipe.</li>
 *      <li>ingredients(): ingredients used in the recipe.</li>
 *      <li>cuisines(): cuisines that this recipe belong to.</li>
 *      <li>allowableDiets(): Diets that allow for this recipe.</li>
 *      <li>intolerantsContained(): Different potential dietary intolerants in the recipe.</li>
 *      <li>nutrients(): Nutritional information about the recipe.</li>
 *      <li>servings(): The amount of servings this recipe makes.</li>
 *      <li>priceInCents(): An approximate cost of the recipe.</li>
 *      <li>healthScore(): A metric that measures how healthy this recipe is.</li>
 *      <li>spoonacularScore(): A metric that takes into account healthiness, preparation difficulty, price, and popularity.</li>
 *      <li>imageURL(): web url to an image of the dish.</li>
 *      <li>sourceURL(): web url of a website with the recipe.</li>
 *      <li>id(): spoonacular's id of the recipe.</li>
 * </ul>
 * <p>
 * @see RecipeSearcher
 */
public final class Recipe {
    private String name;
    private String sourceUrl;
    private String imageUrl;

    private int id;
    private int prepTime;
    private int servings;
    private int price;
    private int healthScore;
    private int spoonacularScore;

    private List<Step> instructions;
    private Map<Ingredient, Amount> ingredients;
    private List<Cuisine> cuisines;
    private List<Diet> diets;
    private List<Intolerant> intolerants;
    private Map<Nutrient, Amount> nutrients;

    static class RecipeBuilder {
        private String name;
        private String sourceUrl;
        private String imageUrl;

        private int id;
        private int prepTime;
        private int servings;
        private int price;
        private int healthScore;
        private int spoonacularScore;

        private List<Step> instructions;
        private Map<Ingredient, Amount> ingredients;
        private List<Cuisine> cuisines;
        private List<Diet> diets;
        private List<Intolerant> intolerants;
        private Map<Nutrient, Amount> nutrients;

        RecipeBuilder name(String n) {
            name = n;
            return this;
        }

        RecipeBuilder sourceUrl(String url) {
            sourceUrl = url;
            return this;
        }

        RecipeBuilder imageUrl(String url) {
            imageUrl = url;
            return this;
        }
    
        RecipeBuilder id(int i) {
            id = i;
            return this;
        }

        RecipeBuilder prepTime(int t) {
            prepTime = t;
            return this;
        }
    
        RecipeBuilder servings(int s) {
            servings = s;
            return this;
        }

        RecipeBuilder pricePerServing(int p) {
            price = p;
            return this;
        }

        RecipeBuilder healthScore(int h) {
            healthScore = h;
            return this;
        }

        RecipeBuilder spoonacularScore(int s) {
            spoonacularScore = s;
            return this;
        }

        RecipeBuilder instructions(List<Step> l) {
            instructions = l;
            return this;
        }
        
        RecipeBuilder ingredients(Map<Ingredient, Amount> l) {
            ingredients = l;
            return this;
        }
        
        RecipeBuilder cuisines(List<Cuisine> l) {
            cuisines = l;
            return this;
        }
        
        RecipeBuilder diets(List<Diet> l) {
            diets = l;
            return this;
        }
        
        RecipeBuilder intolerants(List<Intolerant> l) {
            intolerants = l;
            return this;
        }
        
        RecipeBuilder nutrients(Map<Nutrient, Amount> m) {
            nutrients = m;
            return this;
        }
        
        Recipe build() {
            Recipe r = new Recipe();

            r.cuisines = cuisines;
            r.diets = diets;
            r.healthScore = healthScore;
            r.id = id;
            r.imageUrl = imageUrl;
            r.ingredients = ingredients;
            r.instructions = instructions;
            r.intolerants = intolerants;
            r.name = name;
            r.nutrients = nutrients;
            r.prepTime = prepTime;
            r.price = price;
            r.servings = servings;
            r.sourceUrl = sourceUrl;
            r.spoonacularScore = spoonacularScore;

            return r;
        }
    }

    /**
     * Returns the name of the recipe.
     * @return the name of the recipe.
     */
    public String name() {
        return name;
    }

    /**
     * Returns the id of the recipe from the spoonacular API.
     * @return the id of the recipe from the spoonacular API.
     */
    public int id() {
        return id;
    }

    /**
     * Returns the instructions for preparing the recipe.
     * Instructions are represented as a list of {@code Step}s. 
     * 
     * @see Step
     * 
     * @return the instructions for preparing the recipe.
     */
    public List<Step> instructions() {
        return instructions;
    }

    /**
     * Returns an estimate on how long it takes to prepare the recipe in minutes.
     * @return an estimate on how long it takes to prepare the recipe in minutes.
     */
    public int prepTimeInMinutes() {
        return prepTime;
    }

    /**
     * Returns a map of ingredients to their amounts used to prepare the recipe.
     * 
     * @see Ingredient
     * @see Amount
     *
     * @return a list of ingredients used to prepare the recipe.
     */
    public Map<Ingredient, Amount> ingredients() {
        return ingredients;
    }
    /**
     * Returns a list of cuisines this recipe belongs to.
     * If this recipe does not belong to a specific cuisine, an empty list is returned.
     * 
     * @see Cuisine
     * 
     * @return a list of cuisines this recipe belongs to.
     */
    public List<Cuisine> cuisines() {
        return cuisines;
    }

    /**
     * Returns a list of diets that allow for this recipe.
     * If this recipe is not allowed for any diet, an empty list is returned.
     * 
     * @see Diet
     * 
     * @return a list of diets that allow for this recipe.
     */
    public List<Diet> allowableDiets() {
        return diets;
    }

    /**
     * Returns a list of potential dietary intolerances that this recipe contains.
     * If this recipe does not have any dietary intolerances, an empty list is returned.
     * 
     * @see Intolerant
     * 
     * @return a list of potential dietary intolerances that this recipe contains.
     */
    public List<Intolerant> intolerantsContained() {
        return intolerants;
    }

    /**
     * Returns a map that maps different {@code Nutrient}s to the {@code Amount} of that nutrient in the recipe.
     * {@code Nutrient} contains all support nutrients. The amount is an estimate of the amount of nutrient
     * you will get from one serving of the recipe.
     * 
     * @see Nutrient
     * @see Amount
     * 
     * @return a map that maps different {@code Nutrient}s to the {@code Amount} of that nutrient in the recipe.
     */
    public Map<Nutrient, Amount> nutrients() {
        return nutrients;
    }

    /**
     * Returns the number of servings that preparing this recipe will make.
     * @return the number of servings that preparing this recipe will make.
     */
    public int servings() {
        return servings;
    }

    /**
     * Returns an estimated cost of the recipe's price per serving in cents.
     * @return an estimated cost of the recipe's price per serving in cents.
     */
    public int pricePerServingInCents() {
        return price;
    }

    /**
     * Returns a web url of an image of the recipe.
     * @return a web url of an image of the recipe.
     */
    public String sourceURL() {
        return sourceUrl;
    }

    /**
     * Returns a web url of a website with the recipe.
     * @return a web url of a website with the recipe.
     */
    public String imageURL() {
        return imageUrl;
    }

    /**
     * Returns a metric that measures how healthy this recipe is.
     * The health score is determined by the Spoonacular API and is a scale from 0-100, 0 being least healthy and 100 
     * being most healthy.
     * @return a metric that measures how healthy this recipe is.
     */
    public int healthScore() {
        return healthScore;
    }

    /**
     * Returns A metric approximates how "good" a recipe is.
     * The metric takes into account healthiness, preparation difficulty, price, and popularity.
     * The healthier, the easier it is to prepare, the cheaper, and the more popular the recipe, the higher the score.
     * The score is determined bty the Spoonacular API and is a scale from 0-100.
     * 
     * @return A metric approximates how "good" a recipe is.
     */
    public int spoonacularScore() {
        return spoonacularScore;
    }

}
