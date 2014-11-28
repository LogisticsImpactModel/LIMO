package nl.fontys.limo.simulation.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nl.fontys.limo.simulation.util.MathUtil;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;

/**
 * A SimulationResult encapsulates the data resulting from a simulation of a
 * single supply chain. There is data on the single test cases and accumulated
 * data (min, max, avg) on the whole simulation.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SimulationResult {

    private final SupplyChain supplyChain;
    private DataEntry totalCosts;
    private DataEntry totalLeadTimes;
    private DataEntry totalDelays;
    private DataEntry totalExtraCosts;
    private final Map<String, DataEntry> costsByCategory;
    private final Map<String, DataEntry> leadTimesByCategory;
    private final Map<String, DataEntry> delaysByCategory;
    private final Map<String, DataEntry> extraCostsByCategory;
    private final List<TestCaseResult> testCaseResults;
    private final Map<String, Double> eventExecutionRate;
    private final Map<String, Event> executedEvents;

    public SimulationResult(SupplyChain supplyChain) {
        this.supplyChain = supplyChain;
        this.totalCosts = new DataEntry();
        this.totalLeadTimes = new DataEntry();
        this.totalDelays = new DataEntry();
        this.totalExtraCosts = new DataEntry();
        this.costsByCategory = new ConcurrentHashMap<>();
        this.leadTimesByCategory = new ConcurrentHashMap<>();
        this.delaysByCategory = new ConcurrentHashMap<>();
        this.extraCostsByCategory = new ConcurrentHashMap<>();
        this.testCaseResults = new ArrayList<>();
        this.eventExecutionRate = new ConcurrentHashMap<>();
        this.executedEvents = new ConcurrentHashMap<>();
    }

    public SupplyChain getSupplyChain() {
        return supplyChain;
    }

    public DataEntry getTotalCosts() {
        return totalCosts;
    }

    public DataEntry getTotalLeadTimes() {
        return totalLeadTimes;
    }

    public DataEntry getTotalDelays() {
        return totalDelays;
    }

    public DataEntry getTotalExtraCosts() {
        return totalExtraCosts;
    }

    public Map<String, Event> getExecutedEvents() {
        return executedEvents;
    }

    public Map<String, DataEntry> getCostsByCategory() {
        return Collections.unmodifiableMap(costsByCategory);
    }

    public Map<String, DataEntry> getLeadTimesByCategory() {
        return Collections.unmodifiableMap(leadTimesByCategory);
    }

    public Map<String, DataEntry> getDelaysByCategory() {
        return Collections.unmodifiableMap(delaysByCategory);
    }

    public Map<String, DataEntry> getExtraCostsByCategory() {
        return extraCostsByCategory;
    }

    public List<TestCaseResult> getTestCaseResults() {
        return Collections.unmodifiableList(testCaseResults);
    }

    public Map<String, Double> getEventExecutionRate() {
        return eventExecutionRate;
    }

    /**
     * Adds a test case result to this simulation result.
     *
     * @param tcr Test case result to add.
     */
    public void addTestCaseResult(TestCaseResult tcr) {
        int size = testCaseResults.size();

        // Recalculate totals by adding the new value to the existing data entry.
        this.totalCosts = recalculateDataEntry(totalCosts, size, tcr.getTotalCosts());
        this.totalLeadTimes = recalculateDataEntry(totalLeadTimes, size, tcr.getTotalLeadTimes());
        this.totalDelays = recalculateDataEntry(totalDelays, size, tcr.getTotalDelays());
        this.totalExtraCosts = recalculateDataEntry(totalExtraCosts, size, tcr.getTotalExtraCosts());

        // BY CATEGORY
        for (Map.Entry<String, Double> entry : tcr.getCostsByCategory().entrySet()) {
            DataEntry old = costsByCategory.get(entry.getKey());
            costsByCategory.put(entry.getKey(), recalculateDataEntry(old, size, entry.getValue()));
        }
        for (Map.Entry<String, Double> entry : tcr.getLeadTimesByCategory().entrySet()) {
            DataEntry old = leadTimesByCategory.get(entry.getKey());
            leadTimesByCategory.put(entry.getKey(), recalculateDataEntry(old, size, entry.getValue()));
        }
        for (Map.Entry<String, Double> entry : tcr.getDelaysByCategory().entrySet()) {
            DataEntry old = delaysByCategory.get(entry.getKey());
            delaysByCategory.put(entry.getKey(), recalculateDataEntry(old, size, entry.getValue()));
        }
        for (Map.Entry<String, Double> entry : tcr.getExtraCostsByCategory().entrySet()) {
            DataEntry old = extraCostsByCategory.get(entry.getKey());
            extraCostsByCategory.put(entry.getKey(), recalculateDataEntry(old, size, entry.getValue()));
        }

        // ADD Events
        for (Event event : tcr.getExecutedEvents()) {
            if (!eventExecutionRate.containsKey(event.getUniqueIdentifier())) {
                this.eventExecutionRate.put(event.getUniqueIdentifier(), 1.0 / (size + 1));
                this.executedEvents.put(event.getUniqueIdentifier(), event);
            } else {
                double newAvg = MathUtil.getCumulativeMovingAverage(this.eventExecutionRate.get(event.getUniqueIdentifier()), size, 1);
                this.eventExecutionRate.put(event.getUniqueIdentifier(), newAvg);
            }
        }

        // ADD TCR
        this.testCaseResults.add(tcr);
    }

    /**
     * Recalculate an data entry with new data.
     *
     * @param old Old data entry.
     * @param oldCount Old count used to create average.
     * @param newValue New value to add to data entry.
     * @return Recalculated data entry.
     */
    protected DataEntry recalculateDataEntry(DataEntry old, int oldCount, double newValue) {
        if (old == null) {
            return new DataEntry(newValue, newValue, newValue);
        }

        double min = old.getMin() != null && old.getMin() < newValue ? old.getMin() : newValue;
        double max = old.getMax() != null && old.getMax() > newValue ? old.getMax() : newValue;
        double avg = MathUtil.getCumulativeMovingAverage(old.getAvg(), oldCount, newValue);
        return new DataEntry(min, max, avg);
    }

}
