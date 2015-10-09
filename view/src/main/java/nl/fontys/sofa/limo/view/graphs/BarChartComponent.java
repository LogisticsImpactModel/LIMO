/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.graphs;

import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author nilsh
 */
public class BarChartComponent<T extends AbstractLimoTableModel> {

    private final T tableModel;
    private JFXPanel chartFxPanel; 
    private int panel_width = 675;
    private int panel_height = 400;
    private static final int TABLE_PANEL_HEIGHT_INT = 100;
    private Chart chart;

    public BarChartComponent(T tableModel) {
        this.tableModel = tableModel;
        chartFxPanel= new JFXPanel();
    }

    public BarChartComponent(T tableModel, int panel_width, int panel_height) {
        this.tableModel = tableModel;
        this.panel_height = panel_height;
        this.panel_width = panel_width;
        chartFxPanel = new JFXPanel();
    }

    public void init(JPanel parent, Object constrain, final Axis xAxis, final Axis yAxis) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createBarChart(xAxis, yAxis);
            }
        });
        parent.add(chartFxPanel, constrain);
    }

    private void createBarChart(Axis xAxis, Axis yAxis) {
       //chartFxPanel.setPreferredSize(new Dimension(panel_width, panel_height));
        chart = new BarChart(xAxis, yAxis, tableModel.getXYChartData());
        chartFxPanel.setScene(new Scene(chart));
    }

    private static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.0");

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            value = formatter.format((Number) value);
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

}
