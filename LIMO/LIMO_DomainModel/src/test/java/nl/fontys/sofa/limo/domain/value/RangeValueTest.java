package nl.fontys.sofa.limo.domain.value;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class RangeValueTest {

    /**
     * Test of getValue method, of class RangeValue. initiating RangeValue w/ no
     * params takes min=0, max=1 by default.
     */
    @Test
    public void testGetValue_no_params() {
        System.out.println("getValue");
        RangeValue instance = new RangeValue();
        assertTrue("Value should be smaller or equal than 1", instance.getValue() <= 1);
        assertTrue("Value should be greater or equal to 0", instance.getValue() >= 0);
    }

    /**
     * Test 10 times w/ random max. values whether generated values fall within
     * the range. initiating RangeValue w/ only max range specified, so min=0
     */
    @Test
    public void testGetValue_max_val() {
        System.out.println("getValue");
        Random rand = new Random(20);
        double max;
        for (int i = 0; i < 10; i++) {
            max = rand.nextDouble();
            RangeValue instance = new RangeValue(max);
            assertTrue("Value should be smaller or equal than " + max, instance.getValue() <= max);
            assertTrue("Value should be greater or equal to 0", instance.getValue() >= 0);
        }
    }

    /**
     * Test of setMin method, of class RangeValue.
     */
    @Test
    public void testSetMin() {
        System.out.println("setMin");
        double newMin = 10.0;
        RangeValue instance = new RangeValue();
        instance.setMin(newMin);
        assertEquals("Min should be equal to " + newMin, newMin, instance.getMin(), 0.000001);
    }

    /**
     * Test of getMin method, of class RangeValue.
     */
    @Test
    public void testGetMin() {
        System.out.println("getMin");
        RangeValue instance = new RangeValue();
        assertEquals("Min should be equal to 0.00", 0.00, instance.getMin(), 0.000001);
    }

    /**
     * Test of getMax method, of class RangeValue.
     */
    @Test
    public void testSetMax() {
        System.out.println("setMax");
        double newMax = 10.0;
        RangeValue instance = new RangeValue();
        instance.setMax(newMax);
        assertEquals("Max should be equal to " + newMax + " but isnt", instance.getMax(), newMax, 0.001);
    }

    /**
     * Test of getMax method, of class RangeValue.
     */
    @Test
    public void testGetMax() {
        System.out.println("getMax");
        Random rand = new Random(20);
        for (int i = 0; i < 10; i++) {
            double max = rand.nextDouble();
            RangeValue instance = new RangeValue(max);
            assertEquals("Max should be equal to " + max, max, instance.getMax(), 0.000001);
        }
    }

}
