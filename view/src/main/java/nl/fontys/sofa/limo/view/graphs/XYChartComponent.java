/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.graphs;

import java.awt.Component;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.openide.util.Exceptions;

/**
 *
 * @author nilsh
 */
public class XYChartComponent<T extends AbstractLimoTableModel> {

    Class<? extends XYChart> cl;
    private final T tableModel;
    private final JFXPanel chartFxPanel;
    private int panel_width = 675;
    private int panel_height = 400;
    private static final int TABLE_PANEL_HEIGHT_INT = 100;
    private XYChart chart;
    private JPanel parent;

    public XYChartComponent(T tableModel, Class<? extends XYChart> cl) {
        this.tableModel = tableModel;
        chartFxPanel = new JFXPanel();
        this.cl = cl;
    }

    public XYChartComponent(T tableModel, Class<? extends XYChart> cl, int panel_width, int panel_height) {
        this.tableModel = tableModel;
        this.panel_height = panel_height;
        this.panel_width = panel_width;
        chartFxPanel = new JFXPanel();
        this.cl = cl;
    }

    public void update() {
        chart.setData(tableModel.getXYChartData(cl));
        animateChart();
    }

    public void init(JPanel parent, Object constrain, final Axis xAxis, final Axis yAxis) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createChart(xAxis, yAxis);
            }
        });
        parent.add(chartFxPanel, constrain);
        this.parent = parent;
    }

    public void remove() {
        parent.remove(chartFxPanel);
    }
    Timeline tl = new Timeline();

    private void animateChart() {

        chart.setData(null);
        chart.setAnimated(false);
        final ObservableList<XYChart.Series> tempSet = FXCollections.<XYChart.Series>observableArrayList();
        int frameTime = 1000 / (tableModel.getXYChartData(cl).size());
        tl.stop();
        tl.getKeyFrames().clear();
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(frameTime),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                tempSet.add(tableModel.getXYChartData(cl).get(tempSet.size()));
                                chart.setData(tempSet);
                            }
                        }
                ));
        tl.setCycleCount(tableModel.getXYChartData(cl).size());
        tl.play();

    }

    private void createChart(Axis xAxis, Axis yAxis) {
        //chartFxPanel.setPreferredSize(new Dimension(panel_width, panel_height));
        Constructor<?>[] constructors = cl.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 3) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();

                if (parameterTypes[0] != Axis.class || parameterTypes[1] != Axis.class || parameterTypes[2] != ObservableList.class) {
                    continue;
                } else {
                    try {
                        chart = (XYChart) constructor.newInstance(xAxis, yAxis, tableModel.getXYChartData(cl));
                        animateChart();
                        break;
                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }
        Scene scene = new Scene(chart);;
        chartFxPanel.setScene(scene);
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
