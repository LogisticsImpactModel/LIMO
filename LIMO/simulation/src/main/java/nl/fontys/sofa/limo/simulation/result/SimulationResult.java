package nl.fontys.sofa.limo.simulation.result;

import gnu.trove.procedure.TObjectDoubleProcedure;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.simulation.util.MathUtil;

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
    private final Map<String, DataEntry> costsByNode;
    private final Map<String, DataEntry> leadTimesByNode;
    private final Map<String, DataEntry> delaysByNode;
    private final Map<String, DataEntry> extraCostsByNode;
    private final Map<String, Double> eventExecutionRate;
    private final Map<String, Event> executedEvents;
    private final AtomicInteger testCaseCount;

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
        this.costsByNode = new ConcurrentHashMap<>();
        this.leadTimesByNode = new ConcurrentHashMap<>();
        this.delaysByNode = new ConcurrentHashMap<>();
        this.extraCostsByNode = new ConcurrentHashMap<>();
        this.eventExecutionRate = new ConcurrentHashMap<>();
        this.executedEvents = new ConcurrentHashMap<>();
        this.testCaseCount = new AtomicInteger();
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

    public Map<String, DataEntry> getCostsByNode() {
        return costsByNode;
    }

    public Map<String, DataEntry> getLeadTimesByNode() {
        return leadTimesByNode;
    }

    public Map<String, DataEntry> getDelaysByNode() {
        return delaysByNode;
    }

    public Map<String, DataEntry> getExtraCostsByNode() {
        return extraCostsByNode;
    }

    public Map<String, Double> getEventExecutionRate() {
        return eventExecutionRate;
    }

    public int getTestCaseCount() {
        return testCaseCount.get();
    }

    /**
     * Adds a test case result to this simulation result.
     *
     * @param tcr Test case result to add.
     */
    public void addTestCaseResult(TestCaseResult tcr) {
        final int size = testCaseCount.get();

        // Recalculate totals by adding the new value to the existing data entry.
        this.totalCosts = recalculateDataEntry(totalCosts, size, tcr.getTotalCosts());
        this.totalLeadTimes = recalculateDataEntry(totalLeadTimes, size, tcr.getTotalLeadTimes());
        this.totalDelays = recalculateDataEntry(totalDelays, size, tcr.getTotalDelays());
        this.totalExtraCosts = recalculateDataEntry(totalExtraCosts, size, tcr.getTotalExtraCosts());

        // BY CATEGORY
        tcr.getCostsByCategory().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = costsByCategory.get(key);
                costsByCategory.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getLeadTimesByCategory().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = leadTimesByCategory.get(key);
                leadTimesByCategory.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getDelaysByCategory().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = delaysByCategory.get(key);
                delaysByCategory.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getExtraCostsByCategory().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = extraCostsByCategory.get(key);
                extraCostsByCategory.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        // BY NODE
        tcr.getCostsByNode().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = costsByNode.get(key);
                costsByNode.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getLeadTimesByNode().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = leadTimesByNode.get(key);
                leadTimesByNode.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getDelaysByNode().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = delaysByNode.get(key);
                delaysByNode.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

        tcr.getExtraCostsByNode().forEachEntry(new TObjectDoubleProcedure<String>() {

            @Override
            public boolean execute(String key, double value) {
                DataEntry old = extraCostsByNode.get(key);
                extraCostsByNode.put(key, recalculateDataEntry(old, size, value));
                return true;
            }
        });

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

        this.testCaseCount.incrementAndGet();
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
