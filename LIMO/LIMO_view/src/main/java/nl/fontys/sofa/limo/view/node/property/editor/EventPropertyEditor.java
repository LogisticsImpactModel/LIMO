package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias BrÃ¼ck
 */
public class EventPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return "";
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public Component getCustomEditor() {
        return new CustomEditor();
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    private class CustomEditor extends JPanel implements ActionListener {

        private JLabel lblEvent;
        private JComboBox cbEvents;
        private JTable eventsTable;
        private JButton btnAdd;
        private JButton btnDelete;
        private EventService service;
        private List<Event> eventList;
        private EventTableModel tableModel;
        private ResourceBundle bundle;
        private JComboBox<ExecutionState> box;

        public CustomEditor() {
            bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
            initComponents();
        }

        @Override
        public String getName() {
            return bundle.getString("EVENTS");
        }

        private void initComponents() {
            lblEvent = new JLabel(bundle.getString("EVENT"));
            cbEvents = new JComboBox();
            tableModel = new EventTableModel();
            eventsTable = new JTable(tableModel);
            TableColumn dependencyCol = eventsTable.getColumnModel().getColumn(1);
            box = new JComboBox(ExecutionState.values());
            dependencyCol.setCellEditor(new DefaultCellEditor(box));
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

            JPanel panel = new JPanel(new BorderLayout());
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
                    Event selected = service.findById(eventList.get(cbEvents.getSelectedIndex()).getId());
                    selected.setId(null);
                    selected.setDependency(ExecutionState.INDEPENDENT);
                    tableModel.addEvent(selected);
                    tableModel.fireTableDataChanged(true);
                    btnDelete.setEnabled(true);
                }
            });

            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (eventsTable.getSelectedRow() >= 0) {
                        tableModel.removeEventByID(eventsTable.getSelectedRow());
                        tableModel.fireTableDataChanged(true);
                        if (eventsTable.getRowCount() < 1) {
                            btnDelete.setEnabled(false);
                        }
                    }
                }
            });

            service = Lookup.getDefault().lookup(EventService.class);
            eventList = service.findAll();
            List<String> events = new ArrayList<>();
            btnAdd.setEnabled(!eventList.isEmpty());
            for (Event e : eventList) {
                events.add(e.getName());
            }
            cbEvents.setModel(new DefaultComboBoxModel(events.toArray()));
            tableModel.setEvents((List<Event>) getValue());
            tableModel.fireTableDataChanged(false);
            btnDelete.setEnabled(tableModel.getRowCount() > 0);
            box.addActionListener(this);
        }

        public void setHubView() {
            TableColumn tcol = eventsTable.getColumnModel().getColumn(1);
            eventsTable.getColumnModel().removeColumn(tcol);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(box)) {
                tableModel.getEvents().get(eventsTable.getSelectedRow()).setDependency((ExecutionState)box.getSelectedItem());
                tableModel.fireTableDataChanged(true);
            }
        }

        private class EventTableModel extends AbstractTableModel {

            private List<Event> events;

            public EventTableModel() {
                this.events = new ArrayList<>();
            }

            public EventTableModel(ArrayList<Event> events) {
                this.events = events;
            }

            public List<Event> getEvents() {
                return this.events;
            }

            public void setEvents(List<Event> events) {
                this.events = events;
                this.fireTableDataChanged();
            }

            public void addEvent(Event e) {
                events.add(e);
                events = new ArrayList<>(events);
                setValue(events);
            }

            public void removeEventByID(int i) {
                if (i >= 0 && i < events.size()) {
                    events.remove(i);
                    events = new ArrayList<>(events);
                    setValue(events);
                }
            }
            
            public void save(){
                events = new ArrayList<>(events);
                setValue(events);
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
                save();
            }

            public void fireTableDataChanged(boolean save) {
                super.fireTableDataChanged();
                if (save) {
                    save();
                }
            }
        }
    }
}
