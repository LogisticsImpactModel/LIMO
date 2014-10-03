package nl.fontys.sofa.limo.view.custom;

import java.util.ResourceBundle;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabbedComponentEntries extends JPanel {

    private final JTabbedPane tabbedPane;
    private final ResourceBundle bundle;
    private final JTable costsTable, delaysTable, leadTimesTable;
    private final JScrollPane costsScrollPane, delaysScrollPane, leadTimesScrollPane;

    public TabbedComponentEntries() {
        bundle = ResourceBundle.getBundle("nl.fontys.sofa.limo.view.custom.Bundle");
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        costsTable = new JTable();
        delaysTable = new JTable();
        leadTimesTable = new JTable();
        costsScrollPane = new JScrollPane();
        delaysScrollPane = new JScrollPane();
        leadTimesScrollPane = new JScrollPane();

        costsTable.setModel(new DefaultTableModel(
                new String[][]{
                    {"Pups", "Pups", "Pups"},
                    {"Pups", "Pups", "Pups"},
                    {"Pups", "Pups", "Pups"}
                },
                new String[]{
                    bundle.getString("costs.name"), bundle.getString("costs.category"), bundle.getString("costs.value")
                }
        ));
        costsScrollPane.setViewportView(costsTable);
        tabbedPane.addTab(bundle.getString("costs"), costsScrollPane);

        delaysTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        delaysScrollPane.setViewportView(delaysTable);
        tabbedPane.addTab(bundle.getString("delays"), delaysScrollPane);

        leadTimesTable.setModel(new DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        leadTimesScrollPane.setViewportView(leadTimesTable);
        tabbedPane.addTab(bundle.getString("lead_times"), leadTimesScrollPane);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                        .addContainerGap())
        );
    }

}
