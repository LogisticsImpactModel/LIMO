package nl.fontys.limo.simulation.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.fontys.limo.simulation.result.TestCaseResult;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class TestCase implements Runnable {

    private static Random random;

    private final SupplyChain supplyChain;

    private double totalCosts;
    private double totalLeadTimes;
    private double totalDelays;
    private double totalExtraCosts;
    private Map<String, Double> costsByCategory;
    private Map<String, Double> leadTimesByCategory;
    private Map<String, Double> delaysByCategory;
    private Map<String, Double> extraCostsByCategory;
    private List<Event> executedEvents;

    private long lastDelay;

    private TestCaseResult result;

    public TestCase(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
    }

    public TestCaseResult getResult() {
        return result;
    }

    @Override
    public void run() {
        totalCosts = 0;
        totalLeadTimes = 0;
        totalDelays = 0;
        totalExtraCosts = 0;
        costsByCategory = new HashMap<>();
        leadTimesByCategory = new HashMap<>();
        delaysByCategory = new HashMap<>();
        extraCostsByCategory = new HashMap<>();
        executedEvents = new ArrayList<>();

        lastDelay = 0;
        Node currentNode = supplyChain.getStart();

        while (currentNode != null) {
            Node calcNode = currentNode;
            if (currentNode instanceof ScheduledLeg) {
                // calcNode -> best leg of scheduled leg
                ScheduledLeg sl = (ScheduledLeg) currentNode;
                long acceptTime = lastDelay + sl.getExpectedTime();

                // Get first possible acceptance time
                int first = 0;
                while (first < sl.getAcceptanceTimes().size() && acceptTime < sl.getAcceptanceTimes().get(first)) {
                    first++;
                }

                // No acceptance times, too long wait, times not met -> alternative used
                if (sl.getAcceptanceTimes().isEmpty()
                        || (sl.getAcceptanceTimes().get(--first) - acceptTime > sl.getWaitingTimeLimit())
                        || (sl.getAcceptanceTimes().get(sl.getAcceptanceTimes().size() - 1) - acceptTime > 0)) {
                    calcNode = sl.getAlternative();
                } else {
                    // Calculate the time that has to be waited until acceptance and add to lead times
                    int i = 0;
                    while (acceptTime < sl.getAcceptanceTimes().get(i++)) {
                    }
                    double leadTime = sl.getAcceptanceTimes().get(i - 1) - acceptTime;
                    totalLeadTimes += leadTime;
                    if (!leadTimesByCategory.containsKey(ScheduledLeg.WAIT_CATEGORY)) {
                        leadTimesByCategory.put(ScheduledLeg.WAIT_CATEGORY, leadTime);
                    } else {
                        double lt = leadTimesByCategory.get(ScheduledLeg.WAIT_CATEGORY) + leadTime;
                        leadTimesByCategory.put(ScheduledLeg.WAIT_CATEGORY, lt);
                    }
                }

            } else if (currentNode instanceof MultiModeLeg) {
                // calcNode -> one node based on probability
                MultiModeLeg mml = (MultiModeLeg) currentNode;
                double totalSum = mml.getTotalWeight();
                Random rand = new Random();

                double index = rand.nextDouble() * totalSum;
                double sum = 0;
                int i = 0;
                List<Map.Entry<Leg, Double>> legs = new ArrayList<>(mml.getLegs().entrySet());
                while (sum < index) {
                    sum += legs.get(i++).getValue();
                }
                calcNode = legs.get(i - 1).getKey();
            }

            for (Procedure procedure : calcNode.getProcedures()) {
                double pCost = procedure.getCost().getValue();
                double pLeadTime = procedure.getTimeType().getMinutes(procedure.getTime().getValue());

                totalCosts += pCost;
                totalLeadTimes += pLeadTime;

                if (!costsByCategory.containsKey(procedure.getCategory())) {
                    costsByCategory.put(procedure.getCategory(), 0d);
                }
                double newCost = costsByCategory.get(procedure.getCategory()) + pCost;
                costsByCategory.put(procedure.getCategory(), newCost);

                if (!leadTimesByCategory.containsKey(procedure.getCategory())) {
                    leadTimesByCategory.put(procedure.getCategory(), 0d);
                }
                double newLeadTime = leadTimesByCategory.get(procedure.getCategory()) + pLeadTime;
                leadTimesByCategory.put(procedure.getCategory(), newLeadTime);
            }

            // Calc events
            lastDelay = 0;
            calculateEvents(calcNode, calcNode.getEvents());

            currentNode = currentNode.getNext();
        }

        result = new TestCaseResult(supplyChain, totalCosts, totalLeadTimes, totalDelays, totalExtraCosts, costsByCategory, leadTimesByCategory, delaysByCategory, extraCostsByCategory, executedEvents);
    }

    private void calculateEvents(Component parent, List<Event> events) {
        for (Event event : events) {
            Event parentEvent = parent instanceof Event ? (Event) parent : null;
            // See if should be executed
            if (event.getDependency() == ExecutionState.INDEPENDENT
                    || parent instanceof Hub
                    || parent instanceof Leg
                    || (parentEvent != null && parentEvent.getExecutionState() == event.getDependency())) {

                // Check if event should be executed and set state
                boolean executed = randomDouble() <= event.getProbability().getProbability();
                event.setExecutionState(ExecutionState.stateFromBool(executed));

                // If executed, add costs and times to extra costs and delays from procedures of event
                if (executed) {
                    for (Procedure procedure : event.getProcedures()) {
                        double pExtraCost = procedure.getCost().getValue();
                        double pDelay = procedure.getTimeType().getMinutes(procedure.getTime().getValue());

                        totalExtraCosts += pExtraCost;
                        totalDelays += pDelay;
                        lastDelay += pDelay;

                        if (!extraCostsByCategory.containsKey(procedure.getCategory())) {
                            extraCostsByCategory.put(procedure.getCategory(), 0d);
                        }
                        double newCost = extraCostsByCategory.get(procedure.getCategory()) + pExtraCost;
                        extraCostsByCategory.put(procedure.getCategory(), newCost);

                        if (!delaysByCategory.containsKey(procedure.getCategory())) {
                            delaysByCategory.put(procedure.getCategory(), 0d);
                        }
                        double newLeadTime = delaysByCategory.get(procedure.getCategory()) + pDelay;
                        delaysByCategory.put(procedure.getCategory(), newLeadTime);

                        executedEvents.add(event);
                    }
                }

                // Recursive call to all subevents
                calculateEvents(event, event.getEvents());
            }
        }
    }

    /**
     * Get a random double between 0 and 1 to check if event should be exeuted.
     *
     * @return Random double between 0 and 1.
     */
    private static double randomDouble() {
        if (random == null) {
            random = new Random();
        }

        return random.nextDouble();
    }

}
