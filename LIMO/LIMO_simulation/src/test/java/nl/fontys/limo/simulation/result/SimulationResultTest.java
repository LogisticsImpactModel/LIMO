package nl.fontys.limo.simulation.result;

import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthias Brück
 */
public class SimulationResultTest {

    public SimulationResultTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addTestCaseResult method, of class SimulationResult.
     */
    @Test
    public void testAddTestCaseResult() {
        /*
         System.out.println("addTestCaseResult");
         TestCaseResult tcr = null;
         SimulationResult instance = null;
         instance.addTestCaseResult(tcr);
         // TODO review the generated test code and remove the default call to fail.
         fail("The test case is a prototype.");
         */
    }

    /**
     * Test of recalculateDataEntry method, of class SimulationResult.
     */
    @Test
    public void testRecalculateDataEntry() {
        System.out.println("recalculateDataEntry");
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
