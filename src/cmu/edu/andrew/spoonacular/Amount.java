package cmu.edu.andrew.spoonacular;

import java.lang.UnsupportedOperationException;

/**
 * An immutable quantity of an object with standard measurement units. 
 * <p>
 * An {@code Amount} cannot be constructed; it is returned by the API for methods that require the amount of a certain 
 * object, like {@code Ingredient::amount} or {@code Recipe::nutrients}. The quantity of an {@code Amount} is represented
 * as a double, while the measurement unit is an enum, {@code Unit}.
 * <p>
 * The class has two accessors: {@code quantity()} and {@code unit()}, a conversion method: {@code convertUnit()}, and 
 * a toString method. {@code convertUnit()} can be used to create a new amount with a different unit but with the same
 * physical quantity as the old amount (e.g. 1000 grams will be converted to 1 kilogram).
 * 
 * Sample Code:<br>
 * Amount amnt1 = new Amount(10, GRAMS);<br>
 * Amount amnt2 = amnt1.convertUnit(KILOGRAMS);<br>
 * assertEquals(amnt1.quantity(), amnt2.quantity() * 1000);<br>
 * 
 * @see Amount.Unit
 * 
 */
public final class Amount {

    private Unit unit;

    private double quantity;

    /**
     * Standard measurement units that an {@code Amount} could be.
     * Only{@code Unit}s that measure the same underlying physical property can be converted from one to the other:
     * <p>
     * Volume Units:<br>
     *     TABLESPOONS, TEASPOONS, CUPS, MILLILITERS, FLUID_OUNCES<br>
     * Weight Units:<br>
     *      KILOGRAMS, GRAMS, MILLIGRAMS, MICROGRAMS, POUNDS, OUNCES<br>
     * Misc Units (these units cannot be converted to another unit):<br>
     *      CALORIES, IU, COUNT<br>
     * <p>
     * {@code COUNT} represents a non-standard unit. For example, 12 {@code COUNT} for eggs would mean a dozen eggs.
     * 
     */
    public enum Unit {
        TABLESPOONS, TEASPOONS, CUPS, MILLILITERS, FLUID_OUNCES, 
        KILOGRAMS, GRAMS, MILLIGRAMS, MICROGRAMS, POUNDS, OUNCES, 
        CALORIES, IU, COUNT;

        static Unit fromString(String s) {
            
            switch (s.toLowerCase()) {
                case "tbsp":
                    return TABLESPOONS;
                case "tsp":
                    return TEASPOONS;
                case "cups":
                    return CUPS;
                case "fl oz":
                    return FLUID_OUNCES;
                case "ml":
                    return MILLILITERS;
                case "oz":
                    return OUNCES;
                case "ozs":
                    return OUNCES;
                case "kg":
                    return KILOGRAMS;
                case "g":
                    return GRAMS;
                case "mg":
                    return MILLIGRAMS;
                case "µg":
                    return MICROGRAMS;
                case "lb":
                    return POUNDS;
                case "lbs":
                    return POUNDS;
                case "cal":
                    return CALORIES;
                case "kcal":
                    return CALORIES;
                case "iu":
                    return IU;
                default:
                    return COUNT;
            }
        }

        enum MeasureType {WEIGHT, VOLUME, OTHER}

        MeasureType measureType() {
            switch (this) {
                case TABLESPOONS:
                    return MeasureType.VOLUME;
                case TEASPOONS:
                    return MeasureType.VOLUME;
                case CUPS:
                    return MeasureType.VOLUME;
                case FLUID_OUNCES:
                    return MeasureType.VOLUME;
                case MILLILITERS:
                    return MeasureType.VOLUME;
                case OUNCES:
                    return MeasureType.WEIGHT;
                case KILOGRAMS:
                    return MeasureType.WEIGHT;
                case GRAMS:
                    return MeasureType.WEIGHT;
                case MILLIGRAMS:
                    return MeasureType.WEIGHT;
                case MICROGRAMS:
                    return MeasureType.WEIGHT;
                case POUNDS:
                    return MeasureType.WEIGHT;
                case CALORIES:
                    return MeasureType.OTHER;
                case IU:
                    return MeasureType.OTHER;
                default:
                    return MeasureType.OTHER;
            }
        }

        // volumes use ml as base for conversion, weights use µg
        double base() {
            double res = 0.0;
            switch (this) {
                case TABLESPOONS:
                    res = 14.7868;
                    break;
                case TEASPOONS:
                    res = 4.92892;
                    break;
                case CUPS:
                    res = 236.588;
                    break;
                case FLUID_OUNCES:
                    res = 29.5735;
                    break;
                case MILLILITERS:
                    res = 1;
                    break;
                case KILOGRAMS:
                    res = 1e9;
                    break;
                case GRAMS:
                    res = 1e6;
                    break;
                case MILLIGRAMS:
                    res = 1e3;
                    break;
                case MICROGRAMS:
                    res = 1;
                    break;
                case POUNDS:
                    res = 4.536e8;
                    break;
                case OUNCES:
                    res = 2.835e7;
                    break;
                case CALORIES:
                    throw new UnsupportedOperationException("no conversion from calories.");
                case COUNT:
                    throw new UnsupportedOperationException("no conversion from count.");
                case IU:
                    throw new UnsupportedOperationException("no conversion from iu.");
            }
            return res;
        }
    }
    
    /**
     * Returns the quantity of the amount.
     * @return the quantity of the amount.
     */
    public double quantity() { return quantity; }
    /**
     * Returns the unit of the amount.
     * @return the unit of the amount.
     */
    public Unit unit(){ return unit; }

    Amount(double quantity) {
        this.quantity = quantity;
        this.unit = Unit.COUNT;
    }

    Amount(double quantity, Unit unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Converts the {@code Amount} to a different unit and returns a new {@code Amount} with the new unit. The quantity 
     * of the new {@code Amount} will represent the same amount of volume/weight as the original {@code Amount}.
     * Only{@code Unit}s that measure the same underlying physical property can be converted from one to the other:
     * <p>
     * Volume Units:
     *     TABLESPOONS, TEASPOONS, CUPS, MILLILITERS, FLUID_OUNCES
     * Weight Units:
     *      KILOGRAMS, GRAMS, MILLIGRAMS, MICROGRAMS, POUNDS, OUNCES
     * Misc Units (these units cannot be converted to another unit):
     *      CALORIES, IU, COUNT
     * @param newUnit The unit to be converted into
     * @return a new {@code Amount} with the new unit. The quantity of the new {@code Amount} will represent the same 
     * amount of volume/weight as the original {@code Amount}.
     * @throws UnsupportedOperationException if the {@code Amount} cannot be converted to the {@code newUnit} because
     * it does not meet the specification as mentioned above.
     */
    public Amount convertUnit(Unit newUnit) {
        Unit.MeasureType curr_mt = this.unit.measureType();
        Unit.MeasureType new_mt = newUnit.measureType();
        if (curr_mt == new_mt && curr_mt != Unit.MeasureType.OTHER) {
            double newQuantity = this.quantity * newUnit.base() / this.unit.base();
            return new Amount(newQuantity, newUnit);
        }
        else {
            String errorString = "No supported conversion from " + this.unit.name() + " to " + newUnit.name();
            throw new UnsupportedOperationException(errorString);
        }
    }
    /**
     * Returns a String representation of an Amount. 
     * The form of the string is "{quantity} {unit}".
     * @return a String representation of an Amount. 
     */
    public String toString() {
        return Double.toString(this.quantity) + " " + this.unit.name();
    }

}
