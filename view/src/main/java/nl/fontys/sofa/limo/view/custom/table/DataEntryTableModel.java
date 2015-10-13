package nl.fontys.sofa.limo.view.custom.table;

import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import nl.fontys.sofa.limo.view.graphs.AbstractLimoTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class DataEntryTableModel extends AbstractLimoTableModel {

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
                return LIMOResourceBundle.getString("NAME");
            case 1:
                return LIMOResourceBundle.getString("COSTS MIN");
            case 2:
                return LIMOResourceBundle.getString("COSTS AVG");
            case 3:
                return LIMOResourceBundle.getString("COSTS MAX");
            case 4:
                return LIMOResourceBundle.getString("LEAD TIMES MIN");
            case 5:
                return LIMOResourceBundle.getString("LEAD TIMES AVG");
            case 6:
                return LIMOResourceBundle.getString("LEAD TIMES MAX");
            case 7:
                return LIMOResourceBundle.getString("EXTRA COSTS MIN");
            case 8:
                return LIMOResourceBundle.getString("EXTRA COSTS AVG");
            case 9:
                return LIMOResourceBundle.getString("EXTRA COSTS MAX");
            case 10:
                return LIMOResourceBundle.getString("DELAYS MIN");
            case 11:
                return LIMOResourceBundle.getString("DELAYS AVG");
            case 12:
                return LIMOResourceBundle.getString("DELAYS MAX");
            default:
                return "";
        }
    }

    @Override
    protected ObservableList<XYChart.Series> getBarChartData() {
        ObservableList<XYChart.Series> bcData = FXCollections.<BarChart.Series>observableArrayList();

        for (int i = 1; i < getColumnCount(); i++) {
            XYChart.Series serie = new XYChart.Series();
            ObservableList<XYChart.Data> dataSet = FXCollections.<BarChart.Data>observableArrayList();
            for (int j = 0; j < getRowCount(); j++) {
                XYChart.Data data = new XYChart.Data();
                data.setXValue(names.get(j));
                data.setYValue(getValueAt(j, i));
                dataSet.add(data);
            }
            serie.setData(dataSet);
            serie.setName(getColumnName(i));
            bcData.add(serie);
        }

        return bcData;
    }

}
