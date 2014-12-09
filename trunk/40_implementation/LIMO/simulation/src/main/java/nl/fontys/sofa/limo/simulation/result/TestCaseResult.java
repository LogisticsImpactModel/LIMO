package nl.fontys.sofa.limo.simulation.result;

import gnu.trove.map.TObjectDoubleMap;
import java.util.List;
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

    // TOTALS
    private final double totalCosts;
    private final double totalLeadTimes;
    private final double totalDelays;
    private final double totalExtraCosts;

    // ORDERED BY CATEGORY
    private final TObjectDoubleMap<String> costsByCategory;
    private final TObjectDoubleMap<String> leadTimesByCategory;
    private final TObjectDoubleMap<String> delaysByCategory;
    private final TObjectDoubleMap<String> extraCostsByCategory;

    // ORDERED BY NODE
    private final TObjectDoubleMap<String> costsByNode;
    private final TObjectDoubleMap<String> leadTimesByNode;
    private final TObjectDoubleMap<String> delaysByNode;
    private final TObjectDoubleMap<String> extraCostsByNode;

    private final List<Event> executedEvents;

    public TestCaseResult(
            SupplyChain supplyChain,
            double totalCosts,
            double totalLeadTimes,
            double totalDelays,
            double totalExtraCosts,
            TObjectDoubleMap<String> costsByCategory,
            TObjectDoubleMap<String> leadTimesByCategory,
            TObjectDoubleMap<String> delaysByCategory,
            TObjectDoubleMap<String> extraCostsByCategory,
            TObjectDoubleMap<String> costsByNode,
            TObjectDoubleMap<String> leadTimesByNode,
            TObjectDoubleMap<String> delaysByNode,
            TObjectDoubleMap<String> extraCostsByNode,
            List<Event> executedEvents) {
        this.supplyChain = supplyChain;

        this.totalCosts = totalCosts;
        this.totalLeadTimes = totalLeadTimes;
        this.totalDelays = totalDelays;
        this.totalExtraCosts = totalExtraCosts;

        this.costsByCategory = costsByCategory;
        this.leadTimesByCategory = leadTimesByCategory;
        this.delaysByCategory = delaysByCategory;
        this.extraCostsByCategory = extraCostsByCategory;

        this.costsByNode = costsByNode;
        this.leadTimesByNode = leadTimesByNode;
        this.delaysByNode = delaysByNode;
        this.extraCostsByNode = extraCostsByNode;

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

    public TObjectDoubleMap<String> getCostsByCategory() {
        return costsByCategory;
    }

    public TObjectDoubleMap<String> getLeadTimesByCategory() {
        return leadTimesByCategory;
    }

    public TObjectDoubleMap<String> getDelaysByCategory() {
        return delaysByCategory;
    }

    public TObjectDoubleMap<String> getExtraCostsByCategory() {
        return extraCostsByCategory;
    }

    public TObjectDoubleMap<String> getCostsByNode() {
        return costsByNode;
    }

    public TObjectDoubleMap<String> getLeadTimesByNode() {
        return leadTimesByNode;
    }

    public TObjectDoubleMap<String> getDelaysByNode() {
        return delaysByNode;
    }

    public TObjectDoubleMap<String> getExtraCostsByNode() {
        return extraCostsByNode;
    }

    public List<Event> getExecutedEvents() {
        return executedEvents;
    }

}
