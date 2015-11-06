package nl.fontys.sofa.limo.simulation.result;

import gnu.trove.map.TObjectDoubleMap;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;

/**
 * A TestCaseResult is the result of simulating a single supply chain exactly
 * one time.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class TestCaseResult {

    private final SupplyChain supplyChain;

    // TOTALS
    private final double totalCosts;
    private final double totalLeadTimes;
    private final double totalDelays;
    private final double totalExtraCosts;
    private final double totalCO2;

    // ORDERED BY CATEGORY
    private final TObjectDoubleMap<String> costsByCategory;
    private final TObjectDoubleMap<String> leadTimesByCategory;
    private final TObjectDoubleMap<String> delaysByCategory;
    private final TObjectDoubleMap<String> extraCostsByCategory;
    private final TObjectDoubleMap<String> co2ByCategory;

    // ORDERED BY NODE
    private final TObjectDoubleMap<String> costsByNode;
    private final TObjectDoubleMap<String> leadTimesByNode;
    private final TObjectDoubleMap<String> delaysByNode;
    private final TObjectDoubleMap<String> extraCostsByNode;
    private final TObjectDoubleMap<String> co2ByNode;

    private final List<Event> executedEvents;

    public TestCaseResult(
            SupplyChain supplyChain,
            double totalCosts,
            double totalLeadTimes,
            double totalDelays,
            double totalExtraCosts,
            double totalCO2,
            TObjectDoubleMap<String> costsByCategory,
            TObjectDoubleMap<String> leadTimesByCategory,
            TObjectDoubleMap<String> delaysByCategory,
            TObjectDoubleMap<String> extraCostsByCategory,
            TObjectDoubleMap<String> co2ByCategory,
            TObjectDoubleMap<String> costsByNode,
            TObjectDoubleMap<String> leadTimesByNode,
            TObjectDoubleMap<String> delaysByNode,
            TObjectDoubleMap<String> extraCostsByNode,
            TObjectDoubleMap<String> co2ByNode,
            List<Event> executedEvents) {
        this.supplyChain = supplyChain;

        this.totalCosts = totalCosts;
        this.totalLeadTimes = totalLeadTimes;
        this.totalDelays = totalDelays;
        this.totalExtraCosts = totalExtraCosts;
        this.totalCO2 = totalCO2;

        this.costsByCategory = costsByCategory;
        this.leadTimesByCategory = leadTimesByCategory;
        this.delaysByCategory = delaysByCategory;
        this.extraCostsByCategory = extraCostsByCategory;
        this.co2ByCategory = co2ByCategory;

        this.costsByNode = costsByNode;
        this.leadTimesByNode = leadTimesByNode;
        this.delaysByNode = delaysByNode;
        this.extraCostsByNode = extraCostsByNode;
        this.co2ByNode = co2ByNode;

        this.executedEvents = executedEvents;
    }

    public double getTotalCO2() {
        return totalCO2;
    }

    public TObjectDoubleMap<String> getCo2ByCategory() {
        return co2ByCategory;
    }

    public TObjectDoubleMap<String> getCo2ByNode() {
        return co2ByNode;
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
