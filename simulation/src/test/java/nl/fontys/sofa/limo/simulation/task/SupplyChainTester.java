package nl.fontys.sofa.limo.simulation.task;

import java.util.ArrayList;
import java.util.List;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Base class for supply chain tests which offers a start and end hub which are
 * connected by a leg. Also procedures and events are connected to the hubs and
 * leg.
 *
 * @author Sven Mäurer
 */
public abstract class SupplyChainTester {

    private static final String PORT_VENLO = "Port Venlo";
    private static final String PORT_ROTTERDAM = "Port Rotterdam";
    private static final String LEG = "Rotterdam-Venlo-Express";

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
        start.setName(PORT_ROTTERDAM);
        Location location = new Location(Continent.Europe, SerializableCountry.Netherlands, "Zeeland", "Rotterdam", "1233", "", "");
        start.setLocation(location);
        end = new Hub();
        end.setName(PORT_VENLO);
        end.setLocation(location);
    }

    protected void buildComplexSupplyChain() {
        List<Procedure> procedures = new ArrayList<>();
        procedures.add(new Procedure("loading", MANDATORY, new RangeValue(3000, 4000), new RangeValue(3, 4), TimeType.HOURS, new SingleValue(0)));
        start.setProcedures(procedures);

        Distribution discreteDistribution = new DiscreteDistribution();
        discreteDistribution.setInputValue("X", 1);
        discreteDistribution.setInputValue("Y", 4);
        Event event = new Event("Too late", "You come too late to the hub.", ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        Event subEvent = new Event("Waiting", "Waiting because you were too late.", ExecutionState.EXECUTED, always, ExecutionState.INDEPENDENT);
        List<Procedure> procedures1 = subEvent.getProcedures();
        procedures1.add(new Procedure("waiting", "if too late", new SingleValue(0), new RangeValue(2, 3), TimeType.HOURS, new SingleValue(0)));
        subEvent.setProcedures(procedures1);
        List<Event> events = event.getEvents();
        events.add(subEvent);
        event.setEvents(events);
        List<Event> events1 = start.getEvents();
        events1.add(event);
        start.setEvents(events1);

        List<Procedure> procedures2 = end.getProcedures();
        procedures2.add(new Procedure("unloading", MANDATORY, new RangeValue(2000, 3000), new RangeValue(2, 3), TimeType.HOURS, new SingleValue(0)));
        end.setProcedures(procedures2);
        Event event2 = new Event("Damaged container", "You damage a container.", ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        List<Procedure> procedures3 = event2.getProcedures();
        procedures3.add(new Procedure("costs", "always", new RangeValue(3000, 4000), new SingleValue(0), TimeType.HOURS, new SingleValue(0)));
        event2.setProcedures(procedures3);
        List<Event> events2 = end.getEvents();
        events2.add(event2);
        end.setEvents(events2);

        leg = new Leg();
        leg.setName(LEG);
        Event event3 = new Event("Storm", "Storm slows down the ship.", ExecutionState.INDEPENDENT, always, ExecutionState.INDEPENDENT);
        List<Procedure> procedures4 = event3.getProcedures();
        procedures4.add(new Procedure("waiting", MANDATORY, new SingleValue(0), new RangeValue(30, 60), TimeType.MINUTES, new SingleValue(0)));
        event3.setProcedures(procedures4);
        List<Event> events3 = leg.getEvents();
        events3.add(event3);
        leg.setEvents(events3);
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

        double startHubCosts = result.getCostsByNode().get(PORT_ROTTERDAM);
        assertNotNull(startHubCosts);
        assertTrue(3000 <= startHubCosts);
        assertTrue(4000 >= startHubCosts);

        double endHubsCosts = result.getCostsByNode().get(PORT_VENLO);
        assertNotNull(endHubsCosts);
        assertTrue(2000 <= endHubsCosts);
        assertTrue(3000 >= endHubsCosts);

        double legCosts = result.getCostsByNode().get(LEG);
        assertNotNull(legCosts);
        assertTrue(0 == legCosts);

        result.getDelaysByNode();
        result.getExtraCostsByNode();
        result.getLeadTimesByNode();
    }

}
