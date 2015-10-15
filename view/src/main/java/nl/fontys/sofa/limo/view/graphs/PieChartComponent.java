/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.graphs;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javax.swing.JPanel;

/**
 *
 * @author nilsh
 */
public class PieChartComponent<T extends AbstractLimoTableModel> {

    private final T tableModel;
    private final JFXPanel chartFxPanel;
    private int panel_width = 675;
    private int panel_height = 400;
    private static final int TABLE_PANEL_HEIGHT_INT = 100;
    private PieChart chart;
    private JPanel parent;

    public PieChartComponent(T tableModel) {
        this.tableModel = tableModel;
        chartFxPanel = new JFXPanel();
    }

    public PieChartComponent(T tableModel, int panel_width, int panel_height) {
        this.tableModel = tableModel;
        this.panel_height = panel_height;
        this.panel_width = panel_width;
        chartFxPanel = new JFXPanel();
    }

    public void init(JPanel parent, Object constrain) {
        Platform.runLater(() -> {
            createChart();
        });
        parent.add(chartFxPanel, constrain);
        this.parent = parent;
    }

    public void updateData() {
        chart.setData(tableModel.getPieChartData());
    }

    public void remove() {
        parent.remove(chartFxPanel);
    }

    private void createChart() {
        //chartFxPanel.setPreferredSize(new Dimension(panel_width, panel_height));
        chart = new PieChart(tableModel.getPieChartData());
        chartFxPanel.setScene(new Scene(chart));
    }

}
