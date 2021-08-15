package cmu.edu.andrew.spoonacular;

/**
 * An enum representing a nutrient.
 * Can be used to calculate the percent daily value of an amount of the nutrient.
 */
public enum Nutrient {
    CALORIES (Amount.Unit.CALORIES, 2000, "Calories"),
    FAT (Amount.Unit.GRAMS, 78, "Fat"),
    SATURATED_FAT (Amount.Unit.GRAMS, 20, "Saturated Fat"),
    CARBOHYDRATES (Amount.Unit.GRAMS, 300, "Carbohydrates"),
    SUGAR (Amount.Unit.GRAMS, "Sugar"), // 50
    CHOLESTEROL (Amount.Unit.MILLIGRAMS, 300, "Cholesterol"),
    CHOLINE (Amount.Unit.MILLIGRAMS, 550, "Choline"),
    SODIUM (Amount.Unit.MILLIGRAMS, 2300, "Sodium"),
    FLUORIDE (Amount.Unit.MILLIGRAMS, 4, "Fluoride"),
    PROTEIN (Amount.Unit.GRAMS, 50, "Protein"),
    VITAMIN_K (Amount.Unit.MICROGRAMS, 120, "Vitamin K"),
    MANGANESE (Amount.Unit.MILLIGRAMS, 2.3, "Manganese"),
    VITAMIN_B1 (Amount.Unit.MILLIGRAMS, 1.2, "Vitamin B1"),
    FIBER (Amount.Unit.GRAMS, 28, "Fiber"),
    FOLATE (Amount.Unit.MICROGRAMS, 400, "Folate"),
    FOLIC_ACID (Amount.Unit.MICROGRAMS, 200, "Folic Acid"),
    IRON (Amount.Unit.MILLIGRAMS, 18, "Iron"),
    VITAMIN_B3 (Amount.Unit.MILLIGRAMS, 16, "Vitamin B3"),
    VITAMIN_B2 (Amount.Unit.MILLIGRAMS, 1.2, "Vitamin B2"),
    VITAMIN_C (Amount.Unit.MILLIGRAMS, 90, "Vitamin C"),
    SELENIUM (Amount.Unit.MICROGRAMS, 55, "Selenium"),
    POTASSIUM (Amount.Unit.MILLIGRAMS, 4700, "Potassium"),
    CALCIUM (Amount.Unit.MILLIGRAMS, 1300, "Calcium"),
    PHOSPHORUS (Amount.Unit.MILLIGRAMS, 1250, "Phosphorus"),
    MAGNESIUM (Amount.Unit.MILLIGRAMS, 420, "Magnesium"),
    VITAMIN_D (Amount.Unit.MICROGRAMS, 20, "Vitamin D"),
    VITAMIN_E (Amount.Unit.MILLIGRAMS, 15, "Vitamin E"),
    COPPER (Amount.Unit.MILLIGRAMS, 0.9, "Copper"),
    VITAMIN_B5 (Amount.Unit.MILLIGRAMS, 5, "Vitamin B5"),
    VITAMIN_B6 (Amount.Unit.MILLIGRAMS, 1.7, "Vitamin B6"),
    VITAMIN_B12 (Amount.Unit.MICROGRAMS, 2.4, "Vitamin B12"),
    ZINC (Amount.Unit.MILLIGRAMS, 11, "Zinc"),
    ALCOHOL (Amount.Unit.GRAMS, "Alcohol"), //14
    CAFFEINE (Amount.Unit.MILLIGRAMS, 400, "Caffeine"),
    VITAMIN_A (Amount.Unit.IU, 5000, "Vitamin A"),
    IODINE (Amount.Unit.MICROGRAMS, 150, "Iodine");

    private final Amount.Unit unit;
    private final double dailyValue;
    private String name;

    Nutrient(Amount.Unit unit, String name) {
        this.unit = unit;
        this.dailyValue = 0.0;
        this.name = name;
    }

    Nutrient(Amount.Unit unit, int dailyValue, String name) {
        this.unit = unit;
        this.dailyValue = (double)dailyValue;
        this.name = name;
    }
    
    Nutrient(Amount.Unit unit, double dailyValue, String name) {
        this.unit = unit;
        this.dailyValue = dailyValue;
        this.name = name;
    }

    /**
     * Returns the name of the {@code Nutrient}.
     * @return the name of the {@code Nutrient}.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Returns the default unit the nutrient is commonly displayed in.
     * @return the default unit the nutrient is commonly displayed in.
     */
    public Amount.Unit defaultUnit() {
        return this.unit;
    }

    /**
     * Calculates the percent daily value of a nutrient given the amount.
     * @param amnt The mount of the nutrient.
     * @return the percent daily value of a nutrient given the amount.
     * 
     * @throws UnsupportedOperationException if the nutrient doesn't have a percent daily value.
     */
    public double percentDailyValue(Amount amnt) {
        if (this.dailyValue == 0.0) throw new UnsupportedOperationException("nutrient has no specified daily value");
        double converted = amnt.convertUnit(this.unit).quantity();
        return (converted/this.dailyValue);
    }
    
    /**
     * Returns a nutrient from a string based on name.
     * @param s The name of the nutrient.
     * @return a nutrient from a string based on name.
     * @throws IllegalArgumentException if the name is not a supported nutrient.
     */
    public static Nutrient fromString(String s) {
        for (Nutrient n : Nutrient.values()) {
            if (n.toString().toLowerCase().equals(s.toLowerCase())) {
                return n;
            }
        }
        throw new IllegalArgumentException("nutrient not found in possible enum values");
    }
}
