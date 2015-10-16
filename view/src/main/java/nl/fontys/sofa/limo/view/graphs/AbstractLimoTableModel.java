/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.graphs;

import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nilsh
 */
public abstract class AbstractLimoTableModel extends AbstractTableModel {

    public AbstractLimoTableModel() {
        super();
    }

    public ObservableList<XYChart.Series> getXYChartData(Class<? extends XYChart> cl) {

        String className = cl.getName();

        if (className.equals(LineChart.class.getName())) {
            return getLineChartData();
        }
        if (className.equals(BarChart.class.getName())) {
            return getBarChartData();
        }
        if (className.equals(AreaChart.class.getName())) {
            return getAreaChartData();
        }
        if (className.equals(ScatterChart.class.getName())) {
            return getScatterChartData();
        }
        if (className.equals(BubbleChart.class.getName())) {
            return getBubbleChartData();
        }

        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    protected ObservableList<XYChart.Series> getLineChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    protected ObservableList<XYChart.Series> getBarChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    protected ObservableList<XYChart.Series> getAreaChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    protected ObservableList<XYChart.Series> getScatterChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    protected ObservableList<XYChart.Series> getBubbleChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return new ObservableListWrapper<>(new ArrayList<PieChart.Data>());
    }

}
