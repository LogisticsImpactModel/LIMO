/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.view.graphs.AbstractLimoTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 *
 * @author nilsh
 */
public class SingleCaseTableModel extends AbstractLimoTableModel {

    public static final String COSTS_ID = "COSTS";
    public static final String LEAD_TIMES_ID = "LEAD_TIMES";
    public static final String EXTRA_COSTS_ID = "EXTRA_COSTS";
    public static final String DELAYS_ID = "DELAYS";
    public static final String EVENT_ID = "EVENTS";

    private final List<String> names;
    private final List<Double> costs;
    private final List<Double> leadTimes;
    private final List<Double> extraCosts;
    private final List<Double> delays;
    private final List<Double> eventCounts;

    public SingleCaseTableModel(List<String> names, Map<String, List<Double>> dataEntries) {
        this.names = names;
        this.costs = dataEntries.get(COSTS_ID);
        this.leadTimes = dataEntries.get(LEAD_TIMES_ID);
        this.extraCosts = dataEntries.get(EXTRA_COSTS_ID);
        this.delays = dataEntries.get(DELAYS_ID);
        this.eventCounts = dataEntries.get(EVENT_ID);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return LIMOResourceBundle.getString("NAME");
            case 1:
                return LIMOResourceBundle.getString("COSTS AVG");
            case 2:
                return LIMOResourceBundle.getString("LEAD TIMES AVG");
            case 3:
                return LIMOResourceBundle.getString("EXTRA COSTS AVG");
            case 4:
                return LIMOResourceBundle.getString("DELAYS AVG");
            case 5:
                return "Event Count";
            default:
                return "";

        }
    }

    @Override
    public int getRowCount() {
        return this.names.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return names.get(rowIndex);
            case 1:
                return costs.get(rowIndex);
            case 2:
                return leadTimes.get(rowIndex);
            case 3:
                return extraCosts.get(rowIndex);
            case 4:
                return delays.get(rowIndex);
            case 5:
                return eventCounts.get(rowIndex);
            default:
                return "";
        }
    }

}
