package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javax.swing.event.TableModelListener;
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
    
    private final List<Boolean> enabled;
    private boolean onlyOneEnabled = false;
    
    public DataEntryTableModel(List<String> names, Map<String, List<DataEntry>> dataEntries) {
        this.names = names;
        this.costs = dataEntries.get(COSTS_ID);
        this.leadTimes = dataEntries.get(LEAD_TIMES_ID);
        this.extraCosts = dataEntries.get(EXTRA_COSTS_ID);
        this.delays = dataEntries.get(DELAYS_ID);
        this.enabled = new ArrayList();
        enabled.add(Boolean.FALSE);
        for (int i = 0; i < 12; i++) {
            enabled.add(true);
        }
    }
    
    public void setOnlyOneEnabled(boolean onlyOneEnabled) {
        this.onlyOneEnabled = onlyOneEnabled;
    }
    
    @Override
    public int getRowCount() {
        return this.names.size() + 1;
    }
    
    @Override
    public int getColumnCount() {
        return 13;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (isCellEditable(rowIndex, columnIndex) && rowIndex == 0) {
            if ((Boolean) aValue == true && this.onlyOneEnabled) {
                for (int i = 0; i < enabled.size(); i++) {
                    enabled.set(i, Boolean.FALSE);
                }
            }
            enabled.set(columnIndex, Boolean.valueOf(aValue.toString()));
            
            fireTableDataChanged();
        }
        
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            if (columnIndex == 0) {
                return "Enabled:";
            }
            return enabled.get(columnIndex);
        }
        rowIndex = rowIndex - 1;
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
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 14) {
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return rowIndex == 0 && columnIndex != 0;
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
        List<Integer> activeIndexes = new ArrayList<>();
        for (int i = 1; i < enabled.size(); i++) {
            if (enabled.get(i) == true) {
                activeIndexes.add(i);
            }
        }
        
        for (int i = 0; i < activeIndexes.size(); i++) {
            XYChart.Series serie = new XYChart.Series();
            ObservableList<XYChart.Data> dataSet = FXCollections.<BarChart.Data>observableArrayList();
            for (int j = 1; j < getRowCount(); j++) {
                XYChart.Data data = new XYChart.Data();
                data.setXValue(names.get(j - 1));
                data.setYValue(getValueAt(j, activeIndexes.get(i)));
                dataSet.add(data);
                
            }
            serie.setData(dataSet);
            serie.setName(getColumnName(activeIndexes.get(i)));
            bcData.add(serie);
        }
        
        return bcData;
    }
    
    @Override
    protected ObservableList<XYChart.Series> getLineChartData() {
        ObservableList<XYChart.Series> bcData = FXCollections.<BarChart.Series>observableArrayList();
        List<Integer> activeIndexes = new ArrayList<>();
        for (int i = 1; i < enabled.size(); i++) {
            if (enabled.get(i) == true) {
                activeIndexes.add(i);
            }
        }
        
        for (int i = 0; i < activeIndexes.size(); i++) {
            XYChart.Series serie = new XYChart.Series();
            ObservableList<XYChart.Data> dataSet = FXCollections.<BarChart.Data>observableArrayList();
            Double val = 0.0;
            for (int j = 1; j < getRowCount(); j++) {
                XYChart.Data data = new XYChart.Data();
                data.setXValue(names.get(j - 1));
                Object valTemp = getValueAt(j, activeIndexes.get(i));
                if (valTemp instanceof Number) {
                    Number n = (Number) valTemp;
                    val += n.doubleValue();
                    data.setYValue(val);
                    
                } else {
                    data.setYValue(getValueAt(j, i));
                }
                dataSet.add(data);
            }
            serie.setData(dataSet);
            serie.setName(getColumnName(activeIndexes.get(i)));
            bcData.add(serie);
        }
        
        return bcData;
    }
    
    @Override
    protected ObservableList<XYChart.Series> getAreaChartData() {
        return getLineChartData();
    }
    
    public void removeAllListeners() {
        for (TableModelListener tableModelListener : this.getTableModelListeners()) {
            this.removeTableModelListener(tableModelListener);
        }
    }
    
    @Override
    public ObservableList<PieChart.Data> getPieChartData() {
        ObservableList<PieChart.Data> result = FXCollections.<PieChart.Data>observableArrayList();
        int activeIndex = -1;
        for (int i = 1; i < enabled.size(); i++) {
            if (enabled.get(i) == true && activeIndex < 0) {
                activeIndex = i;
            } else {
                enabled.set(i, Boolean.FALSE);
                
            }
        }
        
        for (int i = 0; i < names.size(); i++) {
            Object val = getValueAt(i + 1, activeIndex);
            if (val instanceof Double) {
                PieChart.Data data = new PieChart.Data(names.get(i), (Double) val);
                result.add(data);
            }
        }
        return result;
    }
    
}
