package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

public final class SubEventsPanel extends JPanel {

    private JLabel lblEvent;
    JComboBox cbEvents;
    JTable eventsTable;
    JButton btnAdd;
    JButton btnDelete;

    private EventDAO eventDAO;
    private List<Event> eventList;
    private EventTableModel tableModel;
    private Event event;
    private static ResourceBundle bundle;

    public SubEventsPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
        initComponents();
    }

    @Override
    public String getName() {
        return bundle.getString("SUB_EVENTS");
    }

    private void initComponents() {
        lblEvent = new JLabel(bundle.getString("EVENT"));
        cbEvents = new JComboBox();

        tableModel = new EventTableModel();
        eventsTable = new JTable(tableModel);
        TableColumn dependencyCol = eventsTable.getColumnModel().getColumn(1);
        dependencyCol.setCellEditor(new DefaultCellEditor(new JComboBox(ExecutionState.values())));

        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnAdd.setEnabled(false);
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        btnDelete.setEnabled(false);
        JPanel panelLeft = new JPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(lblEvent, c);

        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        add(cbEvents, c);

        c.weightx = 0.1;
        c.gridx = 2;
        c.gridy = 0;
        add(btnAdd, c);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);

        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(btnDelete);
        panel.add(panelLeft, BorderLayout.EAST);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = eventDAO.findById(eventList.get(cbEvents.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setDependency(ExecutionState.INDEPENDENT);
                selected.setParent(event);
                tableModel.getEvents().add(selected);
                tableModel.fireTableDataChanged();
                btnDelete.setEnabled(true);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eventsTable.getSelectedRow() >= 0) {
                    tableModel.getEvents().remove(eventsTable.getSelectedRow());
                    tableModel.fireTableDataChanged();
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                eventDAO = Lookup.getDefault().lookup(EventDAO.class);
                eventList = eventDAO.findAll();
                List<String> events = new ArrayList<>();
                btnAdd.setEnabled(!eventList.isEmpty());
                for (Event event : eventList) {
                    events.add(event.getName());
                }
                cbEvents.setModel(new javax.swing.DefaultComboBoxModel(events.toArray()));
            }
        }).start();
    }

    public void update(Event event) {
        if (event != null) {
            this.event = event;
            tableModel.getEvents().addAll(event.getEvents());
            tableModel.fireTableDataChanged();
            btnDelete.setEnabled(tableModel.getRowCount() > 0);
            this.event.setEvents(new ArrayList<Event>());
        }
    }

    public Event getEvent() {
        this.event.getEvents().clear();
        this.event.getEvents().addAll(tableModel.getEvents());
        return this.event;
    }

    private static class EventTableModel extends AbstractTableModel {

        private ArrayList<Event> events;

        public EventTableModel() {
            this.events = new ArrayList<>();
        }

        public EventTableModel(ArrayList<Event> events) {
            this.events = events;
        }

        public ArrayList<Event> getEvents() {
            return this.events;
        }

        public void setEvents(ArrayList<Event> events) {
            this.events = events;
            this.fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return this.events.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public String getColumnName(int column) {
            return column == 0 ? bundle.getString("EVENT") : bundle.getString("DEPENDENCY");
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Event e = this.events.get(rowIndex);
            if (e == null || e.getDependency() == null) {
                return null;
            }

            return columnIndex == 0 ? e.getName() : e.getDependency().name();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 1:
                    this.events.get(rowIndex).setDependency((ExecutionState) aValue);
            }
        }

    }
}
