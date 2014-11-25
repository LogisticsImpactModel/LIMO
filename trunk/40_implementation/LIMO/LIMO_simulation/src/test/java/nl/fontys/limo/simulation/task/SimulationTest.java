package nl.fontys.limo.simulation.task;

import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Sven Mäurer
 */
public class SimulationTest {

    private Simulation simulation;
    private final SupplyChain supplyChain;

    public SimulationTest() {
        supplyChain = new SupplyChain();
        simulation = new Simulation(supplyChain, 5);
        Hub start = new Hub();
        start.addProcedure(new Procedure("loading", "mandatory", new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        //start.addEvent(new Event());
        Hub end = new Hub();
        end.addProcedure(new Procedure("unloading", "mandatory", new RangeValue(2000, 3000), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        Leg leg = new Leg();
        leg.setNext(end);
        start.setNext(leg);
        supplyChain.setStart(start);
    }

    @Test
    public void testGetProgress() {
        double progress = simulation.getProgress();
        Assert.assertEquals(0, progress, 0.000001);
    }

    @Test
    public void testGetResult() {
        SimulationResult result = simulation.getResult();
        Assert.assertEquals(0, result.getTotalCosts().getMax(), 0.000001);
        Assert.assertEquals(0, result.getTotalDelays().getMax(), 0.000001);
        Assert.assertEquals(0, result.getTotalExtraCosts().getMax(), 0.000001);
        Assert.assertEquals(0, result.getTotalLeadTimes().getMax(), 0.000001);
    }

    @Test
    public void testRun() {
//        simulation.run();
//        while (simulation.getProgress() != 1) {
//
//        }
//        SimulationResult result = simulation.getResult();
//        Assert.assertNotNull(result);
//
//        DataEntry totalCosts = result.getTotalCosts();
//        Assert.assertEquals(5000, totalCosts.getMin(), 0.000001);
//        Assert.assertEquals(7000, totalCosts.getMax(), 0.000001);
//        Assert.assertTrue(totalCosts.getAvg() > 5000);
//        Assert.assertTrue(totalCosts.getAvg() < 7000);

//        DataEntry totalDelays = result.getTotalDelays();
//        DataEntry totalExtraCosts = result.getTotalExtraCosts();
//        DataEntry totalLeadTimes = result.getTotalLeadTimes();
    }

    @Test
    public void testTaskFinished() {
        // simulation.taskFinished(Task.EMPTY);
    }

}
