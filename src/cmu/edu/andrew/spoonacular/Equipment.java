package cmu.edu.andrew.spoonacular;
/**
 * Information about an equipment that is used in preparing a recipe.
 * <p>
 * An {@code Equipment} cannot be constructed; it is returned by the API like in {@code Step::Equipments}.
 * <p>
 * The class has two accessors: one for an imageURL of the equipment and one for the name of the equipment.
 * There is also a convenience toString method that is equivalent to the name accessor.
 */
public class Equipment {

    private int id;
    private String name;
    private String imageURL;

    Equipment(int id, String name) {
        this.id = id;
        this.name = name;
        this.imageURL = null;
    }

    Equipment(int id, String name, String imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    int id() {
        return this.id;
    }

    /**
     * Returns the name of this {@code Equipment}.
     * @return the name of this {@code Equipment}.
     */
    public String Name() {
        return this.name;
    }

    /**
     * Returns the name of this {@code Equipment}.
     * @return the name of this {@code Equipment}.
     */
    public String toString() {
        return this.name;
    }
    /**
     * Returns a url of an image of this {@code Equipment}.
     * @return a url of an image of this {@code Equipment}.
     */
    public String ImageURL() {
        if (this.imageURL == null) throw new IllegalStateException("No image url was provided");
        return this.imageURL;
    }

    /**
     * Tests equality of an Object to this Equipment.
     * Returns true if the object is an Equipment and has the same spoonacular id
     * as this Equipment, false otherwise.
     * @param o The Object to be compared to
     * @return true if the object is an Equipment and has the same spoonacular id
     * as this Equipment, false otherwise.
     */
    public boolean equals(Object o) {
        if (o instanceof Equipment)
            return ((Equipment) o).id == id;
        return false;
    }
    
    /**
     * Returns the hash code for this Equipment.
     * Based on equality defined by equals.
     * @return the hash code for this Equipment.
     */
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
