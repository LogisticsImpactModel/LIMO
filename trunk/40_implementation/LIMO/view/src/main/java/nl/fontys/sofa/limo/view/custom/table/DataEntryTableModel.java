package nl.fontys.sofa.limo.view.custom.table;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.simulation.result.DataEntry;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DataEntryTableModel extends AbstractTableModel {

    public static final String COSTS_ID = "COSTS";
    public static final String LEAD_TIMES_ID = "LEAD_TIMES";
    public static final String EXTRA_COSTS_ID = "EXTRA_COSTS";
    public static final String DELAYS_ID = "DELAYS";

    private final List<String> names;
    private final List<DataEntry> costs;
    private final List<DataEntry> leadTimes;
    private final List<DataEntry> extraCosts;
    private final List<DataEntry> delays;

    public DataEntryTableModel(List<String> names, Map<String, List<DataEntry>> dataEntries) {
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
        return 13;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return names.get(rowIndex);
            case 1:
                return costs.get(rowIndex).getMin();
            case 2:
                return costs.get(rowIndex).getAvg();
            case 3:
                return costs.get(rowIndex).getMax();
            case 4:
                return leadTimes.get(rowIndex).getMin();
            case 5:
                return leadTimes.get(rowIndex).getAvg();
            case 6:
                return leadTimes.get(rowIndex).getMax();
            case 7:
                return extraCosts.get(rowIndex).getMin();
            case 8:
                return extraCosts.get(rowIndex).getAvg();
            case 9:
                return extraCosts.get(rowIndex).getMax();
            case 10:
                return delays.get(rowIndex).getMin();
            case 11:
                return delays.get(rowIndex).getAvg();
            case 12:
                return delays.get(rowIndex).getMax();
            default:
                return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Costs Min";
            case 2:
                return "Costs Avg";
            case 3:
                return "Costs Max";
            case 4:
                return "Lead Times Min";
            case 5:
                return "Lead Times Avg";
            case 6:
                return "Lead Times Max";
            case 7:
                return "Extra Costs Min";
            case 8:
                return "Extra Costs Avg";
            case 9:
                return "Extra Costs Max";
            case 10:
                return "Delays Min";
            case 11:
                return "Delays Avg";
            case 12:
                return "Delays Max";
            default:
                return "";
        }
    }

}
