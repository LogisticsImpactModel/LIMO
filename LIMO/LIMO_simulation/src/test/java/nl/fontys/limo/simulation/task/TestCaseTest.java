package nl.fontys.limo.simulation.task;

import java.util.Arrays;
import nl.fontys.limo.simulation.result.TestCaseResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test a single test case for the simulation.
 *
 * @author Sven Mäurer
 */
public class TestCaseTest {

    private SupplyChain supplyChain;
    private TestCase testCase;
    private Hub start, end;
    private final DiscreteDistribution always;
    private MultiModeLeg multiModeLeg;
    private Leg leg;

    public TestCaseTest() {
        always = new DiscreteDistribution();
        always.setInputValue("X", 1);
        always.setInputValue("Y", 1);
    }

    @Before
    public void setUp() {
        supplyChain = new SupplyChain();
        testCase = new TestCase(supplyChain);
        start = new Hub();
        end = new Hub();
    }

    @Test
    public void testGetResult() {
        TestCaseResult result = testCase.getResult();
        assertNull(result);
    }

    @Test
    public void testRunSimpleLeg() {
        buildComplexSupplyChain();

        leg.setNext(end);
        start.setNext(leg);
        supplyChain.setStart(start);

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertComplexSupplyChain(result);
    }

    @Test
    public void testRunMultiModeLegAlwaysHappen() {
        buildComplexSupplyChain();

        supplyChain.setStart(start);
        multiModeLeg = new MultiModeLeg();
        start.setNext(multiModeLeg);
        multiModeLeg.setNext(end);
        multiModeLeg.addLeg(leg, 1);

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertComplexSupplyChain(result);
    }

    @Test
    public void testRunRealMultiModeLeg() {
        buildComplexSupplyChain();

        Leg leg2 = new Leg();
        leg2.setNext(end);
        start.setNext(leg2);

        supplyChain.setStart(start);
        multiModeLeg = new MultiModeLeg();
        start.setNext(multiModeLeg);
        multiModeLeg.setNext(end);
        multiModeLeg.addLeg(leg, 0.4);
        multiModeLeg.addLeg(leg, 0.6);

        testCase = new TestCase(supplyChain);
        testCase.run();

        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertTrue("At least 4 events can happen.", result.getExecutedEvents().size() <= 4);

        assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());
        assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());

        assertTrue("Min 0 based on events.", 0 <= result.getTotalExtraCosts());
        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());

        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());

        assertTrue("Min 5 hours lead time.", 5 * 60 <= result.getTotalLeadTimes());
        assertTrue("Max 7 hours lead time.", 7 * 60 >= result.getTotalLeadTimes());
    }

    //@Test
    public void testScheduledLegTakeAlternative() {
        buildComplexSupplyChain();

        Leg leg2 = new Leg();
        leg2.addProcedure(new Procedure("loading", "mandatory", new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        leg2.setNext(end);
        start.setNext(leg2);

        supplyChain.setStart(start);
        ScheduledLeg scheduledLeg = new ScheduledLeg();
        start.setNext(scheduledLeg);
        scheduledLeg.setNext(end);
        scheduledLeg.setAlternative(leg2);
        scheduledLeg.setWaitingTimeLimit(20);
        scheduledLeg.setAcceptanceTimes(Arrays.asList(new Long[]{(long) 30}));

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());

        assertTrue("Min 3000 based on events.", 3000 <= result.getTotalExtraCosts());
        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());

        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());

        assertTrue("Min 5 hours lead time.", 5 * 60 <= result.getTotalLeadTimes());

        //Alternative
        boolean tooLate = false;
        for (Event executedEvent : result.getExecutedEvents()) {
            if (executedEvent.getName().equals("Waiting")) {
                tooLate = true;
            }
        }
        if (tooLate) {
            assertTrue("Max 11000 based on procedures.", 11000 >= result.getTotalCosts());
            assertTrue("Max 11 hours lead time.", 11 * 60 >= result.getTotalLeadTimes());
        } else {
            assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());
            assertTrue("Max 7 hours lead time.", 7 * 60 >= result.getTotalLeadTimes());
        }
    }

    private void buildComplexSupplyChain() {
        start.addProcedure(new Procedure("loading", "mandatory", new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));

        Distribution discreteDistribution = new DiscreteDistribution();
        discreteDistribution.setInputValue("X", 1);
        discreteDistribution.setInputValue("Y", 4);
        Event event = new Event("Too late", "You come too late to the hub.", start, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        Event subEvent = new Event("Waiting", "Waiting because you were too late.", event, ExecutionState.EXECUTED, always, ExecutionState.INDEPENDENT);
        subEvent.addProcedure(new Procedure("waiting", "if too late", new SingleValue(0), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        event.addEvent(subEvent);
        start.addEvent(event);

        end.addProcedure(new Procedure("unloading", "mandatory", new RangeValue(2000, 3000), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        Event event2 = new Event("Damaged container", "You damage a container.", end, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event2.addProcedure(new Procedure("costs", "always", new RangeValue(3000, 4000), new SingleValue(0), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        end.addEvent(event2);

        leg = new Leg();
        Event event3 = new Event("Storm", "Storm slows down the ship.", leg, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event3.addProcedure(new Procedure("waiting", "mandatory", new SingleValue(0), new RangeValue(30, 60), TimeType.MINUTES, ProcedureResponsibilityDirection.OUTPUT));
        leg.addEvent(event3);
    }

    private void assertComplexSupplyChain(TestCaseResult result) {
        assertTrue("Two events always happen.", result.getExecutedEvents().size() >= 2);
        assertTrue("At least 4 events can happen.", result.getExecutedEvents().size() <= 4);

        assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());
        assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());

        assertTrue("Min 3000 based on events.", 3000 <= result.getTotalExtraCosts());
        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());

        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());

        assertTrue("Min 5 hours lead time.", 5 * 60 <= result.getTotalLeadTimes());
        assertTrue("Max 7 hours lead time.", 7 * 60 >= result.getTotalLeadTimes());
    }

}
