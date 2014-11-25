package nl.fontys.limo.simulation.result;

import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;

/**
 * A TestCaseResult is the result of simulating a single supply chain exactly
 * one time.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class TestCaseResult {

    private final SupplyChain supplyChain;
    private final double totalCosts;
    private final double totalLeadTimes;
    private final double totalDelays;
    private final double totalExtraCosts;
    private final Map<String, Double> costsByCategory;
    private final Map<String, Double> leadTimesByCategory;
    private final Map<String, Double> delaysByCategory;
    private final Map<String, Double> extraCostsByCategory;
    private final List<Event> executedEvents;

    public TestCaseResult(SupplyChain supplyChain, double totalCosts, double totalLeadTimes, double totalDelays, double totalExtraCosts, Map<String, Double> costsByCategory, Map<String, Double> leadTimesByCategory, Map<String, Double> delaysByCategory, Map<String, Double> extraCostsByCategory, List<Event> executedEvents) {
        this.supplyChain = supplyChain;
        this.totalCosts = totalCosts;
        this.totalLeadTimes = totalLeadTimes;
        this.totalDelays = totalDelays;
        this.totalExtraCosts = totalExtraCosts;
        this.costsByCategory = costsByCategory;
        this.leadTimesByCategory = leadTimesByCategory;
        this.delaysByCategory = delaysByCategory;
        this.extraCostsByCategory = extraCostsByCategory;
        this.executedEvents = executedEvents;
    }

    public SupplyChain getSupplyChain() {
        return supplyChain;
    }

    public double getTotalCosts() {
        return totalCosts;
    }

    public double getTotalLeadTimes() {
        return totalLeadTimes;
    }

    public double getTotalDelays() {
        return totalDelays;
    }

    public double getTotalExtraCosts() {
        return totalExtraCosts;
    }

    public Map<String, Double> getCostsByCategory() {
        return costsByCategory;
    }

    public Map<String, Double> getLeadTimesByCategory() {
        return leadTimesByCategory;
    }

    public Map<String, Double> getDelaysByCategory() {
        return delaysByCategory;
    }

    public Map<String, Double> getExtraCostsByCategory() {
        return extraCostsByCategory;
    }

    public List<Event> getExecutedEvents() {
        return executedEvents;
    }

}
