package nl.fontys.sofa.limo.domain.component.procedure.value;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ben
 */
public class RangeValueTest {

    private RangeValue rangeVal;

    @Before
    public void setUp() {
        rangeVal = new RangeValue();
    }

    @After
    public void tearDown() {
        rangeVal = null;
    }

    /**
     * Test of getValue method, of class RangeValue.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        double minVal = 3.0;
        double maxVal = 4.0;
        rangeVal.setMin(minVal);
        rangeVal.setMax(maxVal);
        for (int i = 0; i < 10; i++) {//get 10 times rand no to check whether the test does not pass on coincidence
            double retrievedVal = rangeVal.getValue();
            double min = rangeVal.getMin();
            double max = rangeVal.getMax();
            System.out.println("Retieved val: " + retrievedVal);
            assertEquals(minVal, min, 0.000d);
            assertEquals(maxVal, max, 0.000d);
            assertTrue("Value must be above or equal to " + minVal, retrievedVal >= minVal);
            assertTrue("Value must be below or equal to " + maxVal, retrievedVal <= maxVal);
        }

        rangeVal = new RangeValue(maxVal);
        assertTrue(rangeVal.getMin() == 0);
    }

    /**
     * Test of toString method, of class RangeValue
     */
    @Test
    public void testToString() {
        String expectedString = "0.0 <-> 1.0";
        assertEquals("Output string is not as should be", expectedString, rangeVal.toString());
    }

}
