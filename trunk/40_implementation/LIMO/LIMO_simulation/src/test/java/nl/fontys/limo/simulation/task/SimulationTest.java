package nl.fontys.limo.simulation.task;

import nl.fontys.limo.simulation.result.SimulationResult;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Sven Mäurer
 */
public class SimulationTest extends SupplyChainTester {

    private final Simulation simulation;

    public SimulationTest() {
        super();
        buildComplexSupplyChain();
        leg.setNext(end);
        start.setNext(leg);
        supplyChain.setStart(start);
        simulation = new Simulation(supplyChain, 10);
    }

    @Test
    public void testGetProgress() {
        double progress = simulation.getProgress();
        Assert.assertEquals(0, progress, 0.000001);
    }

    @Test
    public void testGetResult() {
        SimulationResult result = simulation.getResult();
        Assert.assertEquals(null, result.getTotalCosts().getMax());
        Assert.assertEquals(null, result.getTotalDelays().getMax());
        Assert.assertEquals(null, result.getTotalExtraCosts().getMax());
        Assert.assertEquals(null, result.getTotalLeadTimes().getMax());
    }

    @Test
    public void testRun() {
        simulation.run();
//        while (simulation.getProgress() != 1) {
//
//        }
        SimulationResult result = simulation.getResult();

        assertTrue(result.getSupplyChain().equals(supplyChain));

//        assertTrue("Two events always happen.", result.getExecutedEvents().size() >= 2);
//        assertTrue("At least 4 events can happen.", result.getExecutedEvents().size() <= 4);
//
        assertTrue(result.getCostsByCategory().containsKey(MANDATORY));
        //assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());
        //assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());
//
//        assertTrue(result.getExtraCostsByCategory().containsKey(MANDATORY));
//        assertTrue(result.getExtraCostsByCategory().containsKey("always"));
//        assertTrue(result.getExtraCostsByCategory().containsKey("if too late"));
//        assertTrue("Min 3000 based on events.", 3000 <= result.getTotalExtraCosts());
//        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());
//
//        assertTrue(result.getDelaysByCategory().containsKey(MANDATORY));
//        assertTrue(result.getDelaysByCategory().containsKey("always"));
//        assertTrue(result.getDelaysByCategory().containsKey("if too late"));
//        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
//        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());
//
        assertTrue(result.getLeadTimesByCategory().containsKey(MANDATORY));
//        assertTrue("Min 5 hours lead time.", 5 * 60 <= result.getTotalLeadTimes());
//        assertTrue("Max 7 hours lead time.", 7 * 60 >= result.getTotalLeadTimes());
    }

    @Test
    public void testTaskFinished() {
        // simulation.taskFinished(Task.EMPTY);
    }

}
