package nl.fontys.sofa.limo.simulation.task;

import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import nl.fontys.sofa.limo.simulation.result.TestCaseResult;

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
    private TObjectDoubleMap<String> costsByCategory;
    private TObjectDoubleMap<String> leadTimesByCategory;
    private TObjectDoubleMap<String> delaysByCategory;
    private TObjectDoubleMap<String> extraCostsByCategory;
    private TObjectDoubleMap<String> costsByNode;
    private TObjectDoubleMap<String> leadTimesByNode;
    private TObjectDoubleMap<String> delaysByNode;
    private TObjectDoubleMap<String> extraCostsByNode;
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
        costsByCategory = new TObjectDoubleHashMap<>(12);
        leadTimesByCategory = new TObjectDoubleHashMap<>(12);
        delaysByCategory = new TObjectDoubleHashMap<>(12);
        extraCostsByCategory = new TObjectDoubleHashMap<>(12);
        costsByNode = new TObjectDoubleHashMap<>(12);
        leadTimesByNode = new TObjectDoubleHashMap<>(12);
        delaysByNode = new TObjectDoubleHashMap<>(12);
        extraCostsByNode = new TObjectDoubleHashMap<>(12);
        executedEvents = new ArrayList<>();

        lastDelay = 0;
        Node currentNode = supplyChain.getStartHub();

        while (currentNode != null) {
            Node calcNode = currentNode;
            if (currentNode instanceof ScheduledLeg) {
                calcNode = calculateScheduledLeg(currentNode, calcNode);

            } else if (currentNode instanceof MultiModeLeg) {
                calcNode = calculateMultiModeLeg(currentNode);
            }

            calculateProcedures(calcNode);

            // Calc events
            lastDelay = 0;
            calculateEvents(currentNode.getName(), calcNode, calcNode.getEvents());

            currentNode = currentNode.getNext();
        }

        result = new TestCaseResult(supplyChain, totalCosts, totalLeadTimes, totalDelays, totalExtraCosts, costsByCategory, leadTimesByCategory, delaysByCategory, extraCostsByCategory, costsByNode, leadTimesByNode, delaysByNode, extraCostsByNode, executedEvents);
    }

    private Node calculateScheduledLeg(Node currentNode, Node calcNode) {
        // calcNode -> best leg of scheduled leg
        ScheduledLeg sl = (ScheduledLeg) currentNode;
        long acceptTime = lastDelay + sl.getExpectedTime();
        // Get first possible acceptance time
        int index = sl.getAcceptanceTimes().size();
        while (index > 0 && sl.getAcceptanceTimes().get(index - 1) > acceptTime) {
            index--;
        }
        // No acceptance times, too long wait, times not met -> alternative used
        if (sl.getAcceptanceTimes().isEmpty()
                || (index == sl.getAcceptanceTimes().size())
                || (sl.getAcceptanceTimes().get(index) - acceptTime > sl.getWaitingTimeLimit())) {
            calcNode = sl.getAlternative();
        } else {
            // Calculate the time that has to be waited until acceptance and add to lead times
            double leadTime = sl.getAcceptanceTimes().get(index) - acceptTime;
            totalLeadTimes += leadTime;
            if (!leadTimesByCategory.containsKey(ScheduledLeg.WAIT_CATEGORY)) {
                leadTimesByCategory.put(ScheduledLeg.WAIT_CATEGORY, leadTime);
            } else {
                double lt = leadTimesByCategory.get(ScheduledLeg.WAIT_CATEGORY) + leadTime;
                leadTimesByCategory.put(ScheduledLeg.WAIT_CATEGORY, lt);
            }
            if (!leadTimesByNode.containsKey(ScheduledLeg.WAIT_CATEGORY)) {
                leadTimesByNode.put(ScheduledLeg.WAIT_CATEGORY, leadTime);
            } else {
                double lt = leadTimesByNode.get(ScheduledLeg.WAIT_CATEGORY) + leadTime;
                leadTimesByNode.put(ScheduledLeg.WAIT_CATEGORY, lt);
            }
        }
        return calcNode;
    }

    private Node calculateMultiModeLeg(Node currentNode) {
        Node calcNode;
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
        return calcNode;
    }

    private void calculateProcedures(Node calcNode) {
        for (Procedure procedure : calcNode.getProcedures()) {
            double pCost = procedure.getCost().getValue();
            double pLeadTime = procedure.getTimeType().getMinutes(procedure.getTime().getValue());

            totalCosts += pCost;
            totalLeadTimes += pLeadTime;

            if (!costsByCategory.containsKey(procedure.getCategory())) {
                costsByCategory.put(procedure.getCategory(), 0d);
            }
            double newCategoryCost = costsByCategory.get(procedure.getCategory()) + pCost;
            costsByCategory.put(procedure.getCategory(), newCategoryCost);

            if (!costsByNode.containsKey(calcNode.getName())) {
                costsByNode.put(calcNode.getName(), 0d);
            }
            double newNodeCost = costsByNode.get(calcNode.getName()) + pCost;
            costsByNode.put(calcNode.getName(), newNodeCost);

            if (!leadTimesByCategory.containsKey(procedure.getCategory())) {
                leadTimesByCategory.put(procedure.getCategory(), 0d);
            }
            double newCategoryLeadTime = leadTimesByCategory.get(procedure.getCategory()) + pLeadTime;
            leadTimesByCategory.put(procedure.getCategory(), newCategoryLeadTime);

            if (!leadTimesByNode.containsKey(calcNode.getName())) {
                leadTimesByNode.put(calcNode.getName(), 0d);
            }
            double newNodeLeadTime = leadTimesByCategory.get(calcNode.getName()) + pLeadTime;
            leadTimesByNode.put(calcNode.getName(), newNodeLeadTime);
        }
    }

    private void calculateEvents(String nodeKey, Component parent, List<Event> events) {
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
                        if (!extraCostsByNode.containsKey(nodeKey)) {
                            extraCostsByNode.put(nodeKey, 0d);
                        }
                        double newCost = extraCostsByCategory.get(procedure.getCategory()) + pExtraCost;
                        extraCostsByCategory.put(procedure.getCategory(), newCost);
                        extraCostsByNode.put(nodeKey, newCost);

                        if (!delaysByCategory.containsKey(procedure.getCategory())) {
                            delaysByCategory.put(procedure.getCategory(), 0d);
                        }
                        double newCategoryDelay = delaysByCategory.get(procedure.getCategory()) + pDelay;
                        delaysByCategory.put(procedure.getCategory(), newCategoryDelay);

                        if (!delaysByNode.containsKey(nodeKey)) {
                            delaysByNode.put(nodeKey, 0d);
                        }
                        double newNodeDelay = delaysByCategory.get(nodeKey) + pDelay;
                        delaysByNode.put(nodeKey, newNodeDelay);

                        executedEvents.add(event);
                    }
                }

                // Recursive call to all subevents
                calculateEvents(nodeKey, event, event.getEvents());
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
