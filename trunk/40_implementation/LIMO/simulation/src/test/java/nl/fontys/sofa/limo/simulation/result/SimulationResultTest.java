package nl.fontys.sofa.limo.simulation.result;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Matthias Brück
 */
public class SimulationResultTest {

    @Test
    public void testRecalculateDataEntry() {
        SimulationResult instance = new SimulationResult(null);
        int oldCount = 0;
        double newValue = 6.0;
        DataEntry expResult = new DataEntry(6.0, 6.0, 6.0);
        DataEntry result = instance.recalculateDataEntry(null, oldCount, newValue);
        assertEquals(expResult.getMin(), result.getMin(), 0.00000001);
        assertEquals(expResult.getMax(), result.getMax(), 0.00000001);
        assertEquals(expResult.getAvg(), result.getAvg(), 0.00000001);
        DataEntry old = result;
        oldCount++;
        newValue = 10.0;
        expResult = new DataEntry(6.0, 10.0, 8.0);
        result = instance.recalculateDataEntry(old, oldCount, newValue);
        assertEquals(expResult.getMin(), result.getMin(), 0.00000001);
        assertEquals(expResult.getMax(), result.getMax(), 0.00000001);
        assertEquals(expResult.getAvg(), result.getAvg(), 0.00000001);
        old = result;
        oldCount++;
        newValue = 11.0;
        expResult = new DataEntry(6.0, 11.0, 9.0);
        result = instance.recalculateDataEntry(old, oldCount, newValue);
        assertEquals(expResult.getMin(), result.getMin(), 0.00000001);
        assertEquals(expResult.getMax(), result.getMax(), 0.00000001);
        assertEquals(expResult.getAvg(), result.getAvg(), 0.00000001);
        old = result;
        oldCount++;
        newValue = 21.0;
        expResult = new DataEntry(6.0, 21.0, 12.0);
        result = instance.recalculateDataEntry(old, oldCount, newValue);
        assertEquals(expResult.getMin(), result.getMin(), 0.00000001);
        assertEquals(expResult.getMax(), result.getMax(), 0.00000001);
        assertEquals(expResult.getAvg(), result.getAvg(), 0.00000001);
        old = result;
        oldCount++;
        newValue = 2.0;
        expResult = new DataEntry(2.0, 21.0, 10.0);
        result = instance.recalculateDataEntry(old, oldCount, newValue);
        assertEquals(expResult.getMin(), result.getMin(), 0.00000001);
        assertEquals(expResult.getMax(), result.getMax(), 0.00000001);
        assertEquals(expResult.getAvg(), result.getAvg(), 0.00000001);
    }

}
