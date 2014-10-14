///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.fontys.sofa.limo.view.wizard.event;
//
//import java.awt.BorderLayout;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//import javax.swing.Action;
//import javax.swing.BoxLayout;
//import javax.swing.DefaultCellEditor;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableColumn;
//import nl.fontys.sofa.limo.api.dao.DAOFactory;
//import nl.fontys.sofa.limo.api.dao.EventDAO;
//import nl.fontys.sofa.limo.domain.component.Event;
//import nl.fontys.sofa.limo.domain.component.EventExecutionStateDependency;
//import nl.fontys.sofa.limo.view.util.IconUtil;
//import org.openide.util.Lookup;
//
//public final class EventVisualPanel4 extends JPanel {
//
//    /**
//     * Creates new form HubVisualPanel5
//     */
//    public EventVisualPanel4() {
//        initComponents();
//    }
//
//    @Override
//    public String getName() {
//        return "Events";
//    }
//
//    private void initComponents() {
//        tableModel = new EventTableModel();
//        events = new JTable(tableModel);
//        TableColumn dependencyCol = events.getColumnModel().getColumn(1);
//        dependencyCol.setCellEditor(new DefaultCellEditor(new JComboBox(EventExecutionStateDependency.values())));
//
//        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
//        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
//        JPanel panelLeft = new JPanel();
//
//        setLayout(new BorderLayout());
//        add(new JScrollPane(events), BorderLayout.CENTER);
//
//        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
//        panelLeft.add(btnAdd);
//        panelLeft.add(btnDelete);
//        add(panelLeft, BorderLayout.EAST);
//
//        btnAdd.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tableModel.getEvents().add(new Event());
//                tableModel.fireTableDataChanged();
//            }
//        });
//        
//        btnDelete.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (events.getSelectedRow() >= 0) {
//                    tableModel.getEvents().remove(events.getSelectedRow());
//                    tableModel.fireTableDataChanged();
//                }
//            }
//        });
//    }
//
//    private EventDAO eventDAO;
//    private EventTableModel tableModel;
//
//    JTable events;
//    JButton btnAdd;
//    JButton btnDelete;
//
//    private static class EventTableModel extends AbstractTableModel {
//
//        private ArrayList<Event> events;
//
//        public EventTableModel() {
//            events = new ArrayList<>();
//        }
//
//        public EventTableModel(ArrayList<Event> events) {
//            this.events = events;
//        }
//
//        public ArrayList<Event> getEvents() {
//            return events;
//        }
//
//        public void setEvents(ArrayList<Event> events) {
//            this.events = events;
//            this.fireTableDataChanged();
//        }
//
//        @Override
//        public int getRowCount() {
//            return events.size();
//        }
//
//        @Override
//        public int getColumnCount() {
//            return 2;
//        }
//
//        @Override
//        public boolean isCellEditable(int rowIndex, int columnIndex) {
//            return true;
//        }
//
//        @Override
//        public Class<?> getColumnClass(int columnIndex) {
//            return columnIndex == 0 ? Event.class : String.class;
//        }
//
//        @Override
//        public String getColumnName(int column) {
//            return column == 0 ? "Event" : "Dependency";
//        }
//
//        @Override
//        public Object getValueAt(int rowIndex, int columnIndex) {
//            Event e = events.get(rowIndex);
//            if (e == null || e.getDependency() == null) {
//                return null;
//            }
//            
//            return columnIndex == 0 ? e : e.getDependency().name();
//        }
//
//        @Override
//        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//            switch (columnIndex) {
//                case 1:
//                    events.get(rowIndex).setDependency((EventExecutionStateDependency) aValue);
//            }
//        }
//
//    }
//}
