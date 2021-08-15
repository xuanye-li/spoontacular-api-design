package cmu.edu.andrew.spoonacular;

import java.util.List;

/**
 * A step in a recipe.
 * A {@code Step} cannot be constructed; it is returned by the API when retrieving the preparation steps
 * for a recipe.
 * <p>
 * The class has two different accessors and a toString method. The toString method is a text explanation
 * of the perparation step. {@code Ingredients()} returns a list of ingredients needed for this step. 
 * {@code Equipments()} returns a list of equipment needed for this step.
 * <p>
 * @see Recipe
 */
public class Step {
    private String step;
    private List<Ingredient> ingredients;
    private List<Equipment> equipments;

    Step(String step, List<Ingredient> ingredients, List<Equipment> equipments) {
        this.step = step;
        this.ingredients = ingredients;
        this.equipments = equipments;
    }

    /**
     * Returns a text explanation of how to prepare the step.
     * @return a text explanation of how to prepare the step.
     */
    public String toString() {
        return this.step;
    }
    
    /**
     * returns a list of ingredients needed for this step.
     * @return a list of ingredients needed for this step.
     */
    public List<Ingredient> ingredients() {
        return this.ingredients;
    }

    /**
     * returns a list of equipment needed for this step.
     * @return a list of equipment needed for this step.
     */
    public List<Equipment> equipments() {
        return this.equipments;
    }
}
