package nl.fontys.sofa.limo.view.custom;

import java.awt.BorderLayout;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;

public class TabbedComponentEntries<T extends Component> extends JPanel {

    private final String[] columnNames;

    private final JTabbedPane tabbedPane;
    private final ResourceBundle bundle;
    private final JTable costsTable, delaysTable, leadTimesTable;
    private final JScrollPane costsScrollPane, delaysScrollPane, leadTimesScrollPane;

    public TabbedComponentEntries(T component) {
        this.bundle = ResourceBundle.getBundle("nl.fontys.sofa.limo.view.custom.Bundle");
        this.columnNames = new String[]{
            bundle.getString("costs.name"),
            bundle.getString("costs.category"),
            bundle.getString("costs.value")
        };
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        costsTable = new JTable();
        delaysTable = new JTable();
        leadTimesTable = new JTable();
        costsScrollPane = new JScrollPane();
        delaysScrollPane = new JScrollPane();
        leadTimesScrollPane = new JScrollPane();

        costsTable.setModel(new EntryTableModel(component.getCosts()));
        costsScrollPane.setViewportView(costsTable);
        tabbedPane.addTab(bundle.getString("costs"), costsScrollPane);

        delaysTable.setModel(new EntryTableModel(component.getDelays()));
        delaysScrollPane.setViewportView(delaysTable);
        tabbedPane.addTab(bundle.getString("delays"), delaysScrollPane);

        leadTimesTable.setModel(new EntryTableModel(component.getLeadTimes()));
        leadTimesScrollPane.setViewportView(leadTimesTable);
        tabbedPane.addTab(bundle.getString("lead_times"), leadTimesScrollPane);

        /*GroupLayout layout = new GroupLayout(this);
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
         );*/
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabbedPane, BorderLayout.CENTER);
        //this.add(editOptionsPanel, BorderLayout.EAST);

    }

    private class EntryTableModel extends AbstractTableModel {

        private final List<Entry> entries;

        public EntryTableModel(List<Entry> entries) {
            this.entries = entries;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entry.getName();
                case 1:
                    return entry.getCategory();
                case 2:
                    return entry.getValue();
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return entries.size();
        }

    }

}
