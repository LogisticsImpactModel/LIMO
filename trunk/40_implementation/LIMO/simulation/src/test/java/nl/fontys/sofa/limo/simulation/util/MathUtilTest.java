package nl.fontys.sofa.limo.simulation.util;

import nl.fontys.sofa.limo.simulation.util.MathUtil;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Matthias Brück
 */
public class MathUtilTest {

    @Test(expected = InstantiationException.class)
    public void testConstructor() throws InstantiationException {
        new MathUtil();
    }

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
