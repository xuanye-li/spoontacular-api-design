package cmu.edu.andrew.spoonacular;

/**
 * An enum representing a style or method of cooking.
 * Contains one toString method that returns the name of the {@code Cuisine}.
 */
public enum Cuisine {
    AFRICAN("African"),
    AMERICAN("American"),
    BRITISH("British"),
    CAJUN("Cajun"),
    CARIBBEAN("Caribbean"),
    CHINESE("Chinese"),
    EASTERN_EUROPEAN("Eastern European"),
    EUROPEAN("European"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    INDIAN("Indian"),
    IRISH("Irish"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    JEWISH("Jewish"),
    KOREAN("Korean"),
    LATIN_AMERICAN("Latin American"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    MIDDLE_EASTERN("Middle Eastern"),
    NORDIC("Nordic"),
    SOUTHERN("Southern"),
    SPANISH("Spanish"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese");

    private String name;

    private Cuisine(String n) {
        name = n;
    }

    /**
     * Returns the name of the {@code Cuisine}.
     * @return the name of the {@code Cuisine}.
     */
    public String toString() {
        return name;
    }

    /**
     * Returns a {@code Cuisine} from a string based on name.
     * @param s The name of the cuisine.
     * @return a cuisine from a string based on name.
     * @throws IllegalArgumentException if the name is not a supported cuisine.
     */
    public static Cuisine fromString(String s) {
        for(Cuisine c : Cuisine.values())
            if(c.name.equals(s))
                return c;
        
        throw new IllegalArgumentException("Invalid cuisine name");
    }
}