package nl.fontys.limo.simulation.task;

import nl.fontys.limo.simulation.result.TestCaseResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Sven Mäurer
 */
public class TestCaseTest {

    private final SupplyChain supplyChain;
    private final Simulation simulation;
    private final TestCase testCase;

    public TestCaseTest() {
        supplyChain = new SupplyChain();
        simulation = new Simulation(supplyChain, 5);

        Hub start = new Hub();
        start.addProcedure(new Procedure("loading", "mandatory", new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        Distribution discreteDistribution = new DiscreteDistribution();
        discreteDistribution.setInputValue("X", 1);
        discreteDistribution.setInputValue("Y", 4);
        Event event = new Event("Too late", "You come too late to the hub.", start, ExecutionState.INDEPENDENT, discreteDistribution, ExecutionState.INDEPENDENT);
        Distribution always = new DiscreteDistribution();
        always.setInputValue("X", 1);
        always.setInputValue("Y", 1);
        Event subEvent = new Event("Waiting", "Waiting because you were too late.", event, ExecutionState.EXECUTED, always, ExecutionState.INDEPENDENT);
        subEvent.addProcedure(new Procedure("waiting", "mandatory", new SingleValue(0), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        event.addEvent(subEvent);
        start.addEvent(event);

        Hub end = new Hub();
        end.addProcedure(new Procedure("unloading", "mandatory", new RangeValue(2000, 3000), new SingleValue(0), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        Event event2 = new Event("Damage container", "You damage a container.", end, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event2.addProcedure(new Procedure("costs", "mandatory", new RangeValue(3000, 4000), new SingleValue(0), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        end.addEvent(event2);

        Leg leg = new Leg();
        leg.setNext(end);
        start.setNext(leg);
        supplyChain.setStart(start);
        testCase = new TestCase(supplyChain);
    }

    @Test
    public void testGetResult() {
        TestCaseResult result = testCase.getResult();
        assertNull(result);
    }

    @Test
    public void testRun() {
        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);
        assertEquals(1, result.getExecutedEvents().size());
        assertTrue(8000 <= result.getTotalCosts());
        assertTrue(11000 >= result.getTotalCosts());
        assertTrue(5 * 60 <= result.getTotalLeadTimes());
        assertTrue(7 * 60 >= result.getTotalLeadTimes());
    }

}
