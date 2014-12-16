package nl.fontys.sofa.limo.view.topcomponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.simulation.result.DataEntry;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.view.custom.table.DataEntryTableModel;
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

    private SimulationResult result;

    public ResultTopComponent() {
        initComponents();
        setName(Bundle.CTL_ResultTopComponent());
        setToolTipText(Bundle.HINT_ResultTopComponent());
    }

    public ResultTopComponent(SimulationResult result) {
        this();
        this.result = result;

        jTabbedPane1.addTab("Totals", createTotalsPane());
        jTabbedPane1.addTab("By Category", createCategoryPane());
        jTabbedPane1.addTab("By Node", createNodePane());
    }

    private JScrollPane createTotalsPane() {
        Map<String, List<DataEntry>> totalMap = new HashMap<>();
        totalMap.put(DataEntryTableModel.COSTS_ID, Arrays.asList(result.getTotalCosts()));
        totalMap.put(DataEntryTableModel.LEAD_TIMES_ID, Arrays.asList(result.getTotalLeadTimes()));
        totalMap.put(DataEntryTableModel.EXTRA_COSTS_ID, Arrays.asList(result.getTotalExtraCosts()));
        totalMap.put(DataEntryTableModel.DELAYS_ID, Arrays.asList(result.getTotalDelays()));
        DataEntryTableModel detm = new DataEntryTableModel(Arrays.asList("Total"), totalMap);
        return new JScrollPane(new JTable(detm));
    }

    private JScrollPane createCategoryPane() {
        Set<String> categorySet = new HashSet<>();
        categorySet.addAll(result.getCostsByCategory().keySet());
        categorySet.addAll(result.getLeadTimesByCategory().keySet());
        categorySet.addAll(result.getExtraCostsByCategory().keySet());
        categorySet.addAll(result.getDelaysByCategory().keySet());
        List<String> categories = new ArrayList<>(categorySet);
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
        DataEntryTableModel detm = new DataEntryTableModel(categories, map);
        return new JScrollPane(new JTable(detm));
    }

    private JScrollPane createNodePane() {
        List<String> names = new ArrayList<>();
        List<DataEntry> costs = new ArrayList<>();
        List<DataEntry> leadTimes = new ArrayList<>();
        List<DataEntry> extraCosts = new ArrayList<>();
        List<DataEntry> delays = new ArrayList<>();

        Node currentNode = result.getSupplyChain().getStart();
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

        Map<String, List<DataEntry>> map = new HashMap<>();
        map.put(DataEntryTableModel.COSTS_ID, costs);
        map.put(DataEntryTableModel.LEAD_TIMES_ID, leadTimes);
        map.put(DataEntryTableModel.EXTRA_COSTS_ID, extraCosts);
        map.put(DataEntryTableModel.DELAYS_ID, delays);
        DataEntryTableModel detm = new DataEntryTableModel(names, map);
        return new JScrollPane(new JTable(detm));
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
}
