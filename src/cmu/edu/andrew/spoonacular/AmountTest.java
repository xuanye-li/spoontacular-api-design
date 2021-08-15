package cmu.edu.andrew.spoonacular;

import org.junit.Test;

import static cmu.edu.andrew.spoonacular.Amount.Unit.GRAMS;
import static cmu.edu.andrew.spoonacular.Amount.Unit.KILOGRAMS;
import static junit.framework.TestCase.assertEquals;

public class AmountTest {
    @Test
    public void weightConversionTest() {
        Amount test = new Amount(10, GRAMS);
        Amount converted = test.convertUnit(KILOGRAMS);
        assertEquals(test.quantity(), converted.quantity() * 1000);
    }
}