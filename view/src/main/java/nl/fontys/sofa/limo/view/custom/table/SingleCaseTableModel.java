/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import static nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel.COSTS_ID;
import static nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel.DELAYS_ID;
import static nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel.EXTRA_COSTS_ID;
import static nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel.LEAD_TIMES_ID;
import nl.fontys.sofa.limo.view.graphs.AbstractLimoTableModel;

/**
 *
 * @author nilsh
 */
public class SingleCaseTableModel extends AbstractLimoTableModel {

    public static final String COSTS_ID = "COSTS";
    public static final String LEAD_TIMES_ID = "LEAD_TIMES";
    public static final String EXTRA_COSTS_ID = "EXTRA_COSTS";
    public static final String DELAYS_ID = "DELAYS";

    private final List<String> names;
    private final List<Double> costs;
    private final List<Double> leadTimes;
    private final List<Double> extraCosts;
    private final List<Double> delays;

    public SingleCaseTableModel(List<String> names, Map<String, List<Double>> dataEntries) {
        this.names = names;
        this.costs = dataEntries.get(COSTS_ID);
        this.leadTimes = dataEntries.get(LEAD_TIMES_ID);
        this.extraCosts = dataEntries.get(EXTRA_COSTS_ID);
        this.delays = dataEntries.get(DELAYS_ID);
    }

    @Override
    public int getRowCount() {
        return this.names.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
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
            default:
                return "";
        }
    }

}
