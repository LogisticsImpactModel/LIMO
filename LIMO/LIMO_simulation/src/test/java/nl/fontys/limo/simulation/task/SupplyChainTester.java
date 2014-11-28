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
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Sven Mäurer
 */
public abstract class SupplyChainTester {

    protected static final String MANDATORY = "mandatory";

    protected SupplyChain supplyChain;
    protected Leg leg;
    protected Hub start, end;
    protected final DiscreteDistribution always;

    public SupplyChainTester() {
        always = new DiscreteDistribution();
        always.setInputValue("X", 1);
        always.setInputValue("Y", 1);
        supplyChain = new SupplyChain();
        start = new Hub();
        end = new Hub();
    }

    protected void buildComplexSupplyChain() {
        start.addProcedure(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));

        Distribution discreteDistribution = new DiscreteDistribution();
        discreteDistribution.setInputValue("X", 1);
        discreteDistribution.setInputValue("Y", 4);
        Event event = new Event("Too late", "You come too late to the hub.", start, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        Event subEvent = new Event("Waiting", "Waiting because you were too late.", event, ExecutionState.EXECUTED, always, ExecutionState.INDEPENDENT);
        subEvent.addProcedure(new Procedure("waiting", "if too late", new SingleValue(0), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.INPUT));
        event.addEvent(subEvent);
        start.addEvent(event);

        end.addProcedure(new Procedure("unloading", MANDATORY, new RangeValue(2000, 3000), new RangeValue(2, 3), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        Event event2 = new Event("Damaged container", "You damage a container.", end, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event2.addProcedure(new Procedure("costs", "always", new RangeValue(3000, 4000), new SingleValue(0), TimeType.HOURS, ProcedureResponsibilityDirection.OUTPUT));
        end.addEvent(event2);

        leg = new Leg();
        Event event3 = new Event("Storm", "Storm slows down the ship.", leg, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event3.addProcedure(new Procedure("waiting", MANDATORY, new SingleValue(0), new RangeValue(30, 60), TimeType.MINUTES, ProcedureResponsibilityDirection.OUTPUT));
        leg.addEvent(event3);
    }

    protected void assertComplexSupplyChain(TestCaseResult result) {
        assertTrue("Two events always happen.", result.getExecutedEvents().size() >= 2);
        assertTrue("At least 4 events can happen.", result.getExecutedEvents().size() <= 4);

        assertTrue(result.getCostsByCategory().containsKey(MANDATORY));
        assertTrue("Min 5000 based on procedures.", 5000 <= result.getTotalCosts());
        assertTrue("Max 7000 based on procedures.", 7000 >= result.getTotalCosts());

        assertTrue(result.getExtraCostsByCategory().containsKey(MANDATORY));
        assertTrue(result.getExtraCostsByCategory().containsKey("always"));
        assertTrue(result.getExtraCostsByCategory().containsKey("if too late"));
        assertTrue("Min 3000 based on events.", 3000 <= result.getTotalExtraCosts());
        assertTrue("Max 4000 based on events.", 4000 >= result.getTotalExtraCosts());

        assertTrue(result.getDelaysByCategory().containsKey(MANDATORY));
        assertTrue(result.getDelaysByCategory().containsKey("always"));
        assertTrue(result.getDelaysByCategory().containsKey("if too late"));
        assertTrue("No delay can happen.", 0 <= result.getTotalDelays());
        assertTrue("Up to 4 hours can happen.", 4 * 60 >= result.getTotalDelays());

        assertTrue(result.getLeadTimesByCategory().containsKey(MANDATORY));
        assertTrue("Min 5 hours lead time.", 5 * 60 <= result.getTotalLeadTimes());
        assertTrue("Max 7 hours lead time.", 7 * 60 >= result.getTotalLeadTimes());
    }

}
