package nl.fontys.sofa.limo.simulation.task;

import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.hub.Continent;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.hub.SerializableCountry;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.simulation.result.TestCaseResult;
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
        start.setName("Port Rotterdam");
        Location location = new Location(Continent.Europe, SerializableCountry.Netherlands, "Zeeland", "Rotterdam", "1233", "", "");
        start.setLocation(location);
        end = new Hub();
        end.setName("Port Venlo");
        end.setLocation(location);
    }

    protected void buildComplexSupplyChain() {
        start.getProcedures().add(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS));

        Distribution discreteDistribution = new DiscreteDistribution();
        discreteDistribution.setInputValue("X", 1);
        discreteDistribution.setInputValue("Y", 4);
        Event event = new Event("Too late", "You come too late to the hub.", start, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        Event subEvent = new Event("Waiting", "Waiting because you were too late.", event, ExecutionState.EXECUTED, always, ExecutionState.INDEPENDENT);
        subEvent.getProcedures().add(new Procedure("waiting", "if too late", new SingleValue(0), new RangeValue(2, 3), TimeType.HOURS));
        event.getEvents().add(subEvent);
        start.getEvents().add(event);

        end.getProcedures().add(new Procedure("unloading", MANDATORY, new RangeValue(2000, 3000), new RangeValue(2, 3), TimeType.HOURS));
        Event event2 = new Event("Damaged container", "You damage a container.", end, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event2.getProcedures().add(new Procedure("costs", "always", new RangeValue(3000, 4000), new SingleValue(0), TimeType.HOURS));
        end.getEvents().add(event2);

        leg = new Leg();
        leg.setName("Rotterdam-Venlo-Express");
        Event event3 = new Event("Storm", "Storm slows down the ship.", leg, ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        event3.getProcedures().add(new Procedure("waiting", MANDATORY, new SingleValue(0), new RangeValue(30, 60), TimeType.MINUTES));
        leg.getEvents().add(event3);
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
