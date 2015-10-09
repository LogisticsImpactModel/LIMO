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

    public ObservableList<XYChart.Series> getXYChartData() {
        return new ObservableListWrapper<>(new ArrayList<XYChart.Series>());
    }

    public ObservableList<PieChart.Data> getPieChartData() {
        return new ObservableListWrapper<>(new ArrayList<PieChart.Data>());
    }

}
