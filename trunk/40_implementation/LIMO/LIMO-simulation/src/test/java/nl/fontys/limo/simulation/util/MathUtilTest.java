package nl.fontys.limo.simulation.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthias Brück
 */
public class MathUtilTest {
    
    public MathUtilTest() {
    }

    /**
     * Test of getCumulativeMovingAverage method, of class MathUtil.
     */
    @Test
    public void testGetCumulativeMovingAverage() {
        double oldAvg = 0.0;
        double oldCount = 0.0;
        double newValue = 10.0;
        double expResult = 10.0;
        double result = MathUtil.getCumulativeMovingAverage(oldAvg, oldCount, newValue);
        assertEquals(expResult, result, 0.00000001);
        oldAvg = result;
        oldCount++;
        newValue = 16.0;
        expResult = 13.0;
        result = MathUtil.getCumulativeMovingAverage(oldAvg, oldCount, newValue);
        assertEquals(expResult, result, 0.00000001);
        oldAvg = result;
        oldCount++;
        newValue = 16.0;
        expResult = 14.0;
        result = MathUtil.getCumulativeMovingAverage(oldAvg, oldCount, newValue);
        assertEquals(expResult, result, 0.00000001);
        oldAvg = result;
        oldCount++;
        newValue = 6.0;
        expResult = 12.0;
        result = MathUtil.getCumulativeMovingAverage(oldAvg, oldCount, newValue);
        assertEquals(expResult, result, 0.00000001);
    }
}
