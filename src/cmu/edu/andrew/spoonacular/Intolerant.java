package cmu.edu.andrew.spoonacular;

/**
 * An enum representing dietary intolerances that is supported.
 */
public enum Intolerant {
    DAIRY("Dairy"), GLUTEN("Gluten");

    private String name;

    Intolerant(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
