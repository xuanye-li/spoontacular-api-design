package cmu.edu.andrew.spoonacular;

import java.util.HashMap;
import java.util.Map;

/**
 * An immmutable ingredient type used in a recipe. 
 * <p>
 * An {@code Ingredient} cannot be constructed; it is returned by the API for methods that require ingredients, like
 * the ingredients in a recipe by {@code Recipe::ingredients}. An ingredient instance represents a type of food item used
 * in a recipe.
 * <p>
 * The class has four accessors and a toString method. Users can get the name of the ingredient, the price of the ingredient, 
 * and the nutrients in the ingredient. 
 */
public final class Ingredient {
    private int id;
    private String name;
    private int price;
    private Amount amount;
    private Map<Nutrient, Amount> nutrients;

    Ingredient(String name, int id, int price, Amount amount, Map<Nutrient, Amount> nutrients) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.amount = amount;
        this.nutrients = nutrients;
    }

    int id() {
        return id;
    }

    /**
     * Returns the name of the ingredient.
     * @return the name of the ingredient.
     */
    public String name() {
        return this.name;
    }

    /**
     * Returns an approximate cost to purchase a specified amount of the ingredient in US cents.
     * The amount is given as a quantity with a unit.
     * @param quantity The quantity of this ingredient that you want the price for.
     * @param unit The unit of the quantity.
     * @return an approximate cost to purchase a specified amount of the ingredient in US cents.
     * @throws UnsupportedOperationException if there is no known conversion for this ingredient to
     * the input unit.
     */
    public int priceInCents(double quantity, Amount.Unit unit) {
        Amount a = (new Amount(quantity, unit)).convertUnit(amount.unit());
        double ratio = a.quantity() / amount.quantity();

        return (int)Math.round(price * ratio);
    }
    
    /**
     * Returns a map that stores the amount of a given {@code Nutrient} in this {@code Ingredient}.
     * The amount of the ingredient is passed in as input.
     * @param quantity The quantity of this ingredient that you want the nutritional info for.
     * @param unit The unit of the quantity.
     * @return an approximate cost to purchase a specified amount of the ingredient in US cents.
     * @throws UnsupportedOperationException if there is no known conversion for this ingredient to
     * the input unit.
     */
    public Map<Nutrient, Amount> nutrients(double quantity, Amount.Unit unit) {
        Amount a = (new Amount(quantity, unit)).convertUnit(amount.unit());
        double ratio = a.quantity() / amount.quantity();

        Map<Nutrient, Amount> m = new HashMap<Nutrient, Amount>();
        for (Map.Entry<Nutrient, Amount> e : nutrients.entrySet())
            m.put(e.getKey(), new Amount(e.getValue().quantity() * ratio, e.getValue().unit()));

        return m;
    }

    /** 
     * Returns a string representation of this Ingredient.
     * The form of the string is: "{name}".
     * @return a string representation of this Ingredient.
     */
    public String toString() {
        return "{" + this.name + "}";
    }
    /**
     * Tests equality of an Object to this Ingredient.
     * Returns true if the object is an Ingredient and has the same spoonacular id
     * as this Ingredient, false otherwise.
     * @param o The Object to be compared to
     * @return true if the object is an Ingredient and has the same spoonacular id
     * as this Ingredient, false otherwise.
     */
    public boolean equals(Object o) {
        if (o instanceof Ingredient)
            return ((Ingredient) o).id == id;
        return false;
    }
    
    /**
     * Returns the hash code for this Ingredient.
     * Based on equality defined by equals.
     * @return the hash code for this Ingredient.
     */
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
