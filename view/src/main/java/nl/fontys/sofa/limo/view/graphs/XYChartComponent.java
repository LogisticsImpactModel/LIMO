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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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
    private XYChart chart;
    private JPanel parent;

    public XYChartComponent(T tableModel, Class<? extends XYChart> cl) {
        this.tableModel = tableModel;
        chartFxPanel = new JFXPanel();
        this.cl = cl;
    }

    public void updateData() {
        chart.setData(tableModel.getXYChartData(cl));
        animateChart();
    }

    public void init(final JPanel parent, final Object constrain, final Axis xAxis, final Axis yAxis) {
        Platform.runLater(() -> createChart(xAxis, yAxis));

        this.parent = parent;
        SwingUtilities.invokeLater(() -> {
            parent.add(chartFxPanel, constrain);
        });
    }

    public void remove() {
        SwingUtilities.invokeLater(() -> {
            parent.remove(chartFxPanel);
        });
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
                new KeyFrame(Duration.millis(frameTime), (ActionEvent actionEvent) -> {
                    tempSet.add(tableModel.getXYChartData(cl).get(tempSet.size()));
                    chart.setData(tempSet);
                }));
        tl.setCycleCount(tableModel.getXYChartData(cl).size());
        tl.play();

    }

    private void createChart(Axis xAxis, Axis yAxis) {
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
        Scene scene = new Scene(chart);
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
