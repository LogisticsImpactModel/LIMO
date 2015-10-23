package nl.fontys.sofa.limo.view.topcomponent;

import com.oracle.jrockit.jfr.ContentType;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.externaltrader.CSVExporter;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel;
import nl.fontys.sofa.limo.view.custom.table.LimoTable;
import nl.fontys.sofa.limo.view.graphs.GraphSwitchTopComponent;
import nl.fontys.sofa.limo.view.graphs.PieChartComponent;
import nl.fontys.sofa.limo.view.graphs.XYChartComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//nl.fontys.sofa.limo.view.topcomponent//Result//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ResultTopComponent",
        iconBase = "icons/gui/result.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ResultAction",
        preferredID = "ResultTopComponent"
)
@Messages({
    "CTL_ResultAction=Result",
    "CTL_ResultTopComponent=Result Window",
    "HINT_ResultTopComponent=This is a Result window"
})
public final class ResultTopComponent extends TopComponent {

    private List<SimulationResult> results;
    private JTable totalsTable, categoryTable, nodesTable;

    private JPanel nodeGraphPanel, totalGraphPanel, categoryGraphPanel;
    private DataEntryTableModel totalDetm, categoryDetm, nodeDetm;

    public ResultTopComponent() {
        initComponents();
        setName(Bundle.CTL_ResultTopComponent());
        setToolTipText(Bundle.HINT_ResultTopComponent());
    }

    /**
     * @param results a list which must contain at least one entry and maximal 2
     * entries
     */
    public ResultTopComponent(List<SimulationResult> results) throws InstantiationException {
        this();
        if (results.isEmpty() || results.size() > 2) {
            throw new InstantiationException(LIMOResourceBundle.getString("NOT_CORRECT_NUMBER_OF_SIMULATION_RESULT"));
        }
        this.results = results;

        String name = LIMOResourceBundle.getString("DEFAULT_RESULT_WINDOW_NAME");
        if (results.size() == 2) {
            name = LIMOResourceBundle.getString("COMPARISION_RESULT_WINDOW_NAME");
        }

        name = results.stream().map((result) -> " " + result.getSupplyChain().getName().replace(".lsc", "")).reduce(name, String::concat);
        setName(name);
        jTabbedPane1.addTab(LIMOResourceBundle.getString("TOTALS"), createTotalsPane());
        if (results.size() == 1) {
            jTabbedPane1.addTab(LIMOResourceBundle.getString("BY", LIMOResourceBundle.getString("CATEGORY")), createCategoryPane());
            jTabbedPane1.addTab(LIMOResourceBundle.getString("BY", LIMOResourceBundle.getString("NODE")), createNodePane());
        }

        jButton1.addActionListener((ActionEvent e) -> {

            JFileChooser fc = new JFileChooser() {
                @Override
                public void approveSelection() {
                    File f = getSelectedFile();
                    File extended = new File(f.getAbsolutePath().concat(".csv"));

                    if ((f.exists() || extended.exists()) && getDialogType() == SAVE_DIALOG) {
                        int result = JOptionPane.showConfirmDialog(this, LIMOResourceBundle.getString("FILENAME_IN_USE"), LIMOResourceBundle.getString("EXISTING_FILE"), JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (result) {
                            case JOptionPane.YES_OPTION:
                                super.approveSelection();
                                f.delete();
                                return;
                            case JOptionPane.NO_OPTION:
                                return;
                            case JOptionPane.CLOSED_OPTION:
                                return;
                            case JOptionPane.CANCEL_OPTION:
                                cancelSelection();
                                return;
                        }
                    }
                    super.approveSelection();
                }
            };

            String currentPath = fc.getFileSystemView().getDefaultDirectory().toString();
            fc.setCurrentDirectory(new File(currentPath));
            fc.setMultiSelectionEnabled(false);
            fc.setDialogTitle(LIMOResourceBundle.getString("FILE_SAVE_LOCATION"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(LIMOResourceBundle.getString("SIMULATION_RESULTS"), "csv");
            fc.setFileFilter(filter);
            int selection = fc.showSaveDialog(fc);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File saveLocation = fc.getSelectedFile();
                String absolute = saveLocation.getAbsolutePath();

                String ext = "";
                int i = absolute.lastIndexOf('.');
                if (i > 0) {
                    ext = absolute.substring(i);
                }

                if (!ext.equals(".csv")) {
                    absolute = absolute.concat(".csv");
                }

                CSVExporter.exportTables(absolute, new JTable[]{totalsTable, categoryTable, nodesTable}, new String[]{"Totals", "By Categories", "By Nodes"});
            }
        });

    }

    private Component createTotalsPane() {
        Map<String, List<DataEntry>> totalMap = new HashMap<>();
        List<DataEntry> cost = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delay = new ArrayList<>();
        final List<String> name = new ArrayList<>();

        results.stream().map((result) -> {
            cost.add(result.getTotalCosts());
            return result;
        }).map((result) -> {
            leadTimes.add(result.getTotalLeadTimes());
            return result;
        }).map((result) -> {
            extraCosts.add(result.getTotalExtraCosts());
            return result;
        }).map((result) -> {
            delay.add(result.getTotalDelays());
            return result;
        }).forEach((result) -> {
            name.add(result.getSupplyChain().getName().replace(".lsc", ""));
        });
        if (results.size() > 1) {
            cost.add(getDifference(cost.get(0), cost.get(1)));
            leadTimes.add(getDifference(leadTimes.get(0), leadTimes.get(1)));
            extraCosts.add(getDifference(extraCosts.get(0), extraCosts.get(1)));
            delay.add(getDifference(delay.get(0), delay.get(1)));
            name.add(LIMOResourceBundle.getString("RESULT_ABSOULTE_DIFFERENCE_ROW_NAME"));

            cost.add(getDifferenceAsPercentage(cost.get(0), cost.get(1)));
            leadTimes.add(getDifferenceAsPercentage(leadTimes.get(0), leadTimes.get(1)));
            extraCosts.add(getDifferenceAsPercentage(extraCosts.get(0), extraCosts.get(1)));
            delay.add(getDifferenceAsPercentage(delay.get(0), delay.get(1)));
            name.add(LIMOResourceBundle.getString("RESULT_REALTIVE_DIFFERENCE_ROW_NAME"));
        } else {
            name.set(0, LIMOResourceBundle.getString("DEFAULT_RESULT_ROW_NAME"));
        }
        totalMap.put(DataEntryTableModel.COSTS_ID, cost);
        totalMap.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        totalMap.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        totalMap.put(DataEntryTableModel.DELAYS_ID, delay);
        totalDetm = new DataEntryTableModel(name, totalMap);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        totalsTable = new LimoTable(totalDetm);
        JScrollPane totalJScrollPane = new JScrollPane(totalsTable);
        totalJScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 150));
        totalGraphPanel = new JPanel(new BorderLayout());

        createXYChart(totalGraphPanel, totalDetm, BarChart.class);

        panel.add(totalGraphPanel);

        panel.add(totalJScrollPane);
        return panel;

    }

    private void createXYChart(JPanel parent, DataEntryTableModel model, Class<? extends XYChart> chartClass) {
        parent.removeAll();
        XYChartComponent<DataEntryTableModel> chart = new XYChartComponent<>(model, chartClass);
        model.setOnlyOneEnabled(false);
        model.removeAllListeners();
        model.addTableModelListener(
                (TableModelEvent e) -> {
                    Platform.runLater(() -> {
                        chart.updateData();
                    });
                }
        );

        parent.setPreferredSize(new Dimension(500, 700));
        Platform.setImplicitExit(false);
        Platform.runLater(
                () -> {
                    CategoryAxis xAxis = new CategoryAxis();
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 1; i < model.getRowCount(); i++) {
                        names.add(model.getValueAt(i, 0).toString());
                    }

                    xAxis.setCategories(FXCollections.<String>observableArrayList(names));
                    xAxis.setLabel("Categories");

                    NumberAxis yAxis = new NumberAxis();
                    yAxis.setTickUnit(50);
                    yAxis.setLabel("");
                    chart.init(parent, BorderLayout.CENTER, xAxis, yAxis);
                });
    }

    private DataEntry getDifference(DataEntry one, DataEntry two) {
        double min, max, avg;

        min = Math.abs(two.getMin() - one.getMin());
        max = Math.abs(two.getMax() - one.getMax());
        avg = Math.abs(two.getAvg() - one.getAvg());
        return new DataEntry(min, max, avg);
    }

    private DataEntry getDifferenceAsPercentage(DataEntry one, DataEntry two) {
        double min, max, avg;
        min = calculateDifferenceAsPercentage(one.getMin(), two.getMin());
        max = calculateDifferenceAsPercentage(one.getMax(), two.getMax());
        avg = calculateDifferenceAsPercentage(one.getAvg(), two.getAvg());
        return new DataEntry(min, max, avg);
    }

    private double calculateDifferenceAsPercentage(double value1, double value2) {
        double diff = value2 - value1;
        if (value1 <= 0 && value2 > 0) {
            return 100;
        }

        if (value1 <= 0) {
            return 0;
        }
        return diff * 100 / value1;
    }

    private Component createCategoryPane() {
        SimulationResult result = results.get(0);
        Set<String> categorySet = new HashSet<>();
        categorySet.addAll(result.getCostsByCategory().keySet());
        categorySet.addAll(result.getLeadTimesByCategory().keySet());
        categorySet.addAll(result.getExtraCostsByCategory().keySet());
        categorySet.addAll(result.getDelaysByCategory().keySet());
        final List<String> categories = new ArrayList<>(categorySet);
        Collections.sort(categories);

        List<DataEntry> costs = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delays = new ArrayList<>();

        categories.stream().map((category) -> {
            DataEntry cost = result.getCostsByCategory().get(category);
            costs.add(cost == null ? new DataEntry(0, 0, 0) : cost);
            DataEntry leadTime = result.getLeadTimesByCategory().get(category);
            leadTimes.add(leadTime == null ? new DataEntry(0, 0, 0) : leadTime);
            DataEntry extraCost = result.getExtraCostsByCategory().get(category);
            extraCosts.add(extraCost == null ? new DataEntry(0, 0, 0) : extraCost);
            DataEntry delay = result.getDelaysByCategory().get(category);
            return delay;
        }).forEach((delay) -> {
            delays.add(delay == null ? new DataEntry(0, 0, 0) : delay);
        });

        Map<String, List<DataEntry>> map = new HashMap<>();
        map.put(DataEntryTableModel.COSTS_ID, costs);
        map.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        map.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        map.put(DataEntryTableModel.DELAYS_ID, delays);
        categoryDetm = new DataEntryTableModel(categories, map);
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        categoryTable = new LimoTable(categoryDetm);
        JScrollPane catJScrollPane = new JScrollPane(categoryTable);
        categoryGraphPanel = new JPanel(new BorderLayout());
        createPieChart(categoryDetm, categoryGraphPanel);
        panel.add(categoryGraphPanel);
        panel.add(catJScrollPane);
        catJScrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, 150));
        categoryGraphPanel.setPreferredSize(new Dimension(500, 700));

        return panel;
    }

    private void createPieChart(DataEntryTableModel model, JPanel parent) {
        parent.removeAll();
        final PieChartComponent<DataEntryTableModel> chart = new PieChartComponent<>(model);
        model.setOnlyOneEnabled(true);
        model.removeAllListeners();
        model.addTableModelListener((TableModelEvent e) -> {
            if (e.getLastRow() != e.getFirstRow()) {
                Platform.runLater(() -> {
                    chart.updateData();
                });
            }
            if (parent == nodeGraphPanel) {
                nodesTable.tableChanged(e);
            } else if (parent == totalGraphPanel) {
                totalsTable.tableChanged(e);
            } else if (parent == categoryGraphPanel) {
                categoryTable.tableChanged(e);
            }
        });
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            chart.init(parent, BorderLayout.CENTER);
        });
    }

    private Component createNodePane() {
        List<String> names = new ArrayList<>();
        List<DataEntry> costs = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delays = new ArrayList<>();
        results.stream().forEach((result) -> {
            Node currentNode = result.getSupplyChain().getStartHub();
            while (currentNode != null) {
                String name = currentNode.getName();
                names.add(name);

                DataEntry cost = result.getCostsByNode().get(name);
                costs.add(cost == null ? new DataEntry(0, 0, 0) : cost);

                DataEntry leadTime = result.getLeadTimesByNode().get(name);
                leadTimes.add(leadTime == null ? new DataEntry(0, 0, 0) : leadTime);

                DataEntry extraCost = result.getExtraCostsByNode().get(name);
                extraCosts.add(extraCost == null ? new DataEntry(0, 0, 0) : extraCost);

                DataEntry delay = result.getDelaysByNode().get(name);
                delays.add(delay == null ? new DataEntry(0, 0, 0) : delay);

                currentNode = currentNode.getNext();
            }
        });
        Map<String, List<DataEntry>> map = new HashMap<>();
        map.put(DataEntryTableModel.COSTS_ID, costs);
        map.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        map.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        map.put(DataEntryTableModel.DELAYS_ID, delays);
        nodeDetm = new DataEntryTableModel(names, map);
        nodeDetm.setOnlyOneEnabled(false);

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        nodesTable = new LimoTable(nodeDetm);
        nodeGraphPanel = new JPanel(new BorderLayout());
        JScrollPane catJScrollPane = new JScrollPane(nodesTable);
        createXYChart(nodeGraphPanel, nodeDetm, LineChart.class);

        panel.add(nodeGraphPanel);

        panel.add(catJScrollPane);

        catJScrollPane.setPreferredSize(
                new Dimension(Integer.MAX_VALUE, 150));
        nodeGraphPanel.setPreferredSize(
                new Dimension(500, 700));

        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ResultTopComponent.class, "ResultTopComponent.jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(500, 500));
        jTabbedPane1.setRequestFocusEnabled(false);
        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    GraphSwitchTopComponent graphSwitch = (GraphSwitchTopComponent) WindowManager.getDefault().findTopComponent("GraphSwitchTopComponent");

    @Override
    public void componentOpened() {
        GraphChangeListener listener = new GraphChangeListener();
        associateLookup(Lookups.singleton(listener));
    }

    @Override
    public void componentClosed() {

    }

    void writeProperties(java.util.Properties p
    ) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p
    ) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    public class GraphChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() instanceof Class) {
                Class<? extends Chart> graphClass = (Class<? extends Chart>) e.getSource();
                Component selectedComponent = jTabbedPane1.getSelectedComponent();
                DataEntryTableModel detm;
                JPanel componet;
                if (SwingUtilities.isDescendingFrom(nodeGraphPanel, selectedComponent)) {
                    System.out.println("Node");
                    detm = nodeDetm;
                    componet = nodeGraphPanel;
                } else if (SwingUtilities.isDescendingFrom(totalGraphPanel, selectedComponent)) {
                    System.out.println("Total");
                    detm = totalDetm;
                    componet = totalGraphPanel;
                } else if (SwingUtilities.isDescendingFrom(categoryGraphPanel, selectedComponent)) {
                    System.out.println("Category");
                    detm = categoryDetm;
                    componet = categoryGraphPanel;
                } else {
                    return;
                }

                if (graphClass == PieChart.class) {
                    createPieChart(detm, componet);
                } else {
                    createXYChart(componet, detm, (Class<? extends XYChart>) graphClass);
                }
            }

        }

    }

}
