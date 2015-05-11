package nl.fontys.sofa.limo.simulation.task;

import java.util.Arrays;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.simulation.result.TestCaseResult;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Test a single test case for the simulation. This includes normal legs,
 * scheduled legs and multi mode legs.
 *
 * @author Sven Mäurer
 */
public class TestCaseTest extends SupplyChainTester {

    private TestCase testCase;
    private MultiModeLeg multiModeLeg;

    public TestCaseTest() {
        super();
    }

    @Before
    public void setUp() {
        testCase = new TestCase(supplyChain);
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
        supplyChain.setStartHub(start);

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertTrue(result.getSupplyChain().equals(supplyChain));
        assertComplexSupplyChain(result);
    }

    @Test
    public void testRunMultiModeLegAlwaysHappen() {
        buildComplexSupplyChain();

        supplyChain.setStartHub(start);
        multiModeLeg = new MultiModeLeg();
        start.setNext(multiModeLeg);
        multiModeLeg.setNext(end);
        multiModeLeg.addLeg(leg, 1);

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertTrue(result.getSupplyChain().equals(supplyChain));
        assertComplexSupplyChain(result);
    }

    @Test
    public void testRunRealMultiModeLeg() {
        buildComplexSupplyChain();

        Leg leg2 = new Leg();
        leg2.setName("Leg 2");
        leg2.setNext(end);
        start.setNext(leg2);

        supplyChain.setStartHub(start);
        multiModeLeg = new MultiModeLeg();
        multiModeLeg.setName("Multimode Leg");
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

    @Test
    public void testScheduledLegTakeAlternative() {
        buildComplexSupplyChain();

        Leg leg2 = new Leg();
        leg2.setName("Leg 2");
        leg2.getProcedures().add(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS));
        leg2.setNext(end);
        start.setNext(leg2);

        supplyChain.setStartHub(start);
        ScheduledLeg scheduledLeg = new ScheduledLeg();
        scheduledLeg.setName("Scheduleg Leg");
        start.setNext(scheduledLeg);
        scheduledLeg.setNext(end);
        scheduledLeg.setAlternative(leg2);
        scheduledLeg.setWaitingTimeLimit(20);
        scheduledLeg.setAcceptanceTimes(Arrays.asList(new Long[]{30L}));

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

    @Test
    public void testScheduledLegNotAlternative() {
        buildComplexSupplyChain();

        Hub middleHub = new Hub();
        middleHub.setName("Amsterdam");
        middleHub.getProcedures().add(new Procedure("customs control", MANDATORY, new SingleValue(100), new RangeValue(3, 4), TimeType.HOURS));

        Leg leg2 = new Leg();
        leg2.setName("Rotterdam--Amsterdam");
        leg2.getProcedures().add(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS));
        leg2.setNext(middleHub);

        supplyChain.setStartHub(start);
        ScheduledLeg scheduledLeg = new ScheduledLeg();
        scheduledLeg.setName("Scheduleg Leg");
        start.setNext(scheduledLeg);
        scheduledLeg.setNext(middleHub);
        scheduledLeg.setAlternative(leg2);
        scheduledLeg.setWaitingTimeLimit(4 * 60);
        scheduledLeg.setAcceptanceTimes(Arrays.asList(new Long[]{30L, 120L, 180L}));

        Leg leg3 = new Leg();
        leg3.setName("Leg 3");
        leg3.getProcedures().add(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS));
        leg3.setNext(middleHub);

        ScheduledLeg scheduledLeg2 = new ScheduledLeg();
        scheduledLeg2.setName("Scheduled Leg 2");
        middleHub.setNext(scheduledLeg2);
        scheduledLeg2.setNext(end);
        scheduledLeg2.setAlternative(leg3);
        scheduledLeg2.setWaitingTimeLimit(4 * 60);
        scheduledLeg2.setAcceptanceTimes(Arrays.asList(new Long[]{30L, 120L, 180L}));

        testCase.run();
        TestCaseResult result = testCase.getResult();
        assertNotNull(result);

        assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());
        assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());

        assertTrue("Min 3000 based on events.", 3000 <= result.getTotalExtraCosts());
        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());

        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());

        // Add the waiting time here
        assertTrue(result.getLeadTimesByCategory().containsKey(ScheduledLeg.WAIT_CATEGORY));
        assertTrue("Min 5.5 hours lead time.", 5 * 60 + 30 <= result.getTotalLeadTimes());
        assertTrue("Max 11.5 hours lead time.", 7 * 60 + 180 * 2 >= result.getTotalLeadTimes());
    }

}
