package nl.fontys.sofa.limo.view.topcomponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.externaltrader.CSVExporter;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel;
import nl.fontys.sofa.limo.view.graphs.AbstractLimoTableModel;
import nl.fontys.sofa.limo.view.graphs.BarChartComponent;
import nl.fontys.sofa.limo.view.graphs.SampleLimoTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

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

        for (SimulationResult result : results) {
            name += " " + result.getSupplyChain().getName().replace(".lsc", "");
        }
        setName(name);

        jTabbedPane1.addTab(LIMOResourceBundle.getString("TOTALS"), createTotalsPane());
        if (results.size() == 1) {
            jTabbedPane1.addTab(LIMOResourceBundle.getString("BY", LIMOResourceBundle.getString("CATEGORY")), createCategoryPane());
            jTabbedPane1.addTab(LIMOResourceBundle.getString("BY", LIMOResourceBundle.getString("NODE")), createNodePane());
        }
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                String currentPath = fc.getFileSystemView().getDefaultDirectory().toString();
                fc.setCurrentDirectory(new File(currentPath));
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fc.showOpenDialog(jTabbedPane1) == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getPath();
                    String filename = (String) JOptionPane.showInputDialog(jTabbedPane1, LIMOResourceBundle.getString("CHOOSE_FILE"), LIMOResourceBundle.getString("CHOOSE_FILE"), JOptionPane.PLAIN_MESSAGE);
                    if (filename != null) {
                        if (!filename.isEmpty()) {
                            CSVExporter.exportTables(path + "\\" + filename + ".csv", new JTable[]{totalsTable, categoryTable, nodesTable}, new String[]{"Totals", "By Categories", "By Nodes"});
                        }
                    }
                }
            }
        });
    }

    private JScrollPane createTotalsPane() {
        Map<String, List<DataEntry>> totalMap = new HashMap<>();
        List<DataEntry> cost = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delay = new ArrayList<>();
        List<String> name = new ArrayList<>();

        for (SimulationResult result : results) {
            cost.add(result.getTotalCosts());
            leadTimes.add(result.getTotalLeadTimes());
            extraCosts.add(result.getTotalExtraCosts());
            delay.add(result.getTotalDelays());
            name.add(result.getSupplyChain().getName().replace(".lsc", ""));
        }
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
        DataEntryTableModel detm = new DataEntryTableModel(name, totalMap);
        totalsTable = new JTable(detm);
        return new JScrollPane(totalsTable);
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

    private JScrollPane createCategoryPane() {
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

        for (String category : categories) {
            DataEntry cost = result.getCostsByCategory().get(category);
            costs.add(cost == null ? new DataEntry(0, 0, 0) : cost);

            DataEntry leadTime = result.getLeadTimesByCategory().get(category);
            leadTimes.add(leadTime == null ? new DataEntry(0, 0, 0) : leadTime);

            DataEntry extraCost = result.getExtraCostsByCategory().get(category);
            extraCosts.add(extraCost == null ? new DataEntry(0, 0, 0) : extraCost);

            DataEntry delay = result.getDelaysByCategory().get(category);
            delays.add(delay == null ? new DataEntry(0, 0, 0) : delay);
        }

        Map<String, List<DataEntry>> map = new HashMap<>();
        map.put(DataEntryTableModel.COSTS_ID, costs);
        map.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        map.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        map.put(DataEntryTableModel.DELAYS_ID, delays);
        final DataEntryTableModel detm = new DataEntryTableModel(categories, map);

        final JPanel panel = new JPanel(new BorderLayout());

        categoryTable = new JTable(detm);
        JScrollPane catJScrollPane = new JScrollPane(categoryTable);
        panel.add(catJScrollPane, BorderLayout.SOUTH);
        final BarChartComponent<DataEntryTableModel> chart = new BarChartComponent<>(detm, 300, 300);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                CategoryAxis xAxis = new CategoryAxis();
                xAxis.setCategories(FXCollections.<String>observableArrayList(categories));
                xAxis.setLabel("Categories");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setTickUnit(50);
                yAxis.setLabel("");
                chart.init(panel, BorderLayout.CENTER, xAxis, yAxis);
            }
        });
        return new JScrollPane(panel);
    }

    private JScrollPane createNodePane() {
        List<String> names = new ArrayList<>();
        List<DataEntry> costs = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delays = new ArrayList<>();
        for (SimulationResult result : results) {
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
        }
        Map<String, List<DataEntry>> map = new HashMap<>();
        map.put(DataEntryTableModel.COSTS_ID, costs);
        map.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        map.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        map.put(DataEntryTableModel.DELAYS_ID, delays);
        DataEntryTableModel detm = new DataEntryTableModel(names, map);
        nodesTable = new JTable(detm);
        final JPanel panel = new JPanel(new BorderLayout());

        categoryTable = new JTable(detm);
        JScrollPane catJScrollPane = new JScrollPane(categoryTable);
        panel.add(catJScrollPane, BorderLayout.SOUTH);
        final BarChartComponent<DataEntryTableModel> chart = new BarChartComponent<>(detm, 300, 300);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                CategoryAxis xAxis = new CategoryAxis();
                SupplyChain chain = results.get(0).getSupplyChain();
                ArrayList<String> names = new ArrayList<>();
                Node node = chain.getStartHub();
                while (node != null) {
                    if (node instanceof Hub) {
                        Hub hub = (Hub) node;
                        names.add(hub.getName());
                    }
                    node = node.getNext();
                }

                xAxis.setCategories(FXCollections.<String>observableArrayList(names));
                xAxis.setLabel("Categories");

                NumberAxis yAxis = new NumberAxis();
                yAxis.setTickUnit(50);
                yAxis.setLabel("");
                chart.init(panel, BorderLayout.CENTER, xAxis, yAxis);
            }
        });
        return new JScrollPane(panel);
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

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(500, 500));
        jTabbedPane1.setRequestFocusEnabled(false);
        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }
}
