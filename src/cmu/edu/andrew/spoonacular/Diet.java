package cmu.edu.andrew.spoonacular;

/**
 * An enum representing a diet.
 * Contains one toString method that returns the name of the {@code Diet}.
 */
public enum Diet {
    GLUTEN_FREE("gluten free"),
    KETOGENIC("ketogenic"),
    VEGETARIAN("vegetarian"),
    LACTO_VEGETARIAN("lacto vegetarian"),
    OVO_VEGETARIAN("ovo vegetarian"),
    VEGAN("vegan"),
    PESCETARIAN("pescetarian"),
    PALEO("paleo"),
    PRIMAL("primal"),
    WHOLE30("whole30");

    private String name;

    private Diet(String n) {
        name = n;
    }

    /**
     * Returns the name of the {@code Diet}.
     * @return the name of the {@code Diet}.
     */
    public String toString() {
        return name;
    }

    /**
     * Returns a diet from a string based on name.
     * @param s The name of the diet.
     * @return a diet from a string based on name.
     * @throws IllegalArgumentException if the name is not a supported diet.
     */
    public static Diet fromString(String s) {
        for(Diet d : Diet.values())
            if(d.name.equals(s))
                return d;

        throw new IllegalArgumentException("Invalid diet name");
    }
}
