package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias BrÃƒÂ¼ck
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

    private class CustomEditor extends JPanel {

        private JLabel lbl_event;
        private JComboBox cbox_availableEvents;
        private JComboBox<ExecutionState> cbox_executionState;
        private JTable tbl_usedEvents;
        private JButton btn_add, btn_delete;
        private EventService service;
        private List<Event> eventList;
        private EventTableModel tblmdl_eventsModel;
        private ResourceBundle bundle;
        private JPanel panel_table, panel_right;

        public CustomEditor() {
            bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
            //INIT COMPONENTS
            initComponents();
            //ADD LAYOUT AND COMPONENTS
            addLayoutAndComponents();
            //ADD DATA
            addData();
            //ENABLE BUTTONS
            enableButtons();
            //ADD ACTION LISTENERS
            addActionListeners();
        }

        @Override
        public String getName() {
            return bundle.getString("EVENTS");
        }

        private void initComponents() {
            lbl_event = new JLabel(bundle.getString("EVENT"));
            cbox_availableEvents = new JComboBox();
            tblmdl_eventsModel = new EventTableModel();
            tbl_usedEvents = new JTable(tblmdl_eventsModel);
            TableColumn dependencyCol = tbl_usedEvents.getColumnModel().getColumn(1);
            cbox_executionState = new JComboBox(ExecutionState.values());
            dependencyCol.setCellEditor(new DefaultCellEditor(cbox_executionState));
            btn_add = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
            btn_add.setEnabled(false);
            btn_delete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
            btn_delete.setEnabled(false);
            panel_table = new JPanel(new BorderLayout());
            panel_right = new JPanel();

        }

        private void addLayoutAndComponents() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            //ADD COMPONENTS TO LAYOUT
            c.weightx = 0.2;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            add(lbl_event, c);
            c.weightx = 0.7;
            c.gridx = 1;
            c.gridy = 0;
            add(cbox_availableEvents, c);
            c.weightx = 0.1;
            c.gridx = 2;
            c.gridy = 0;
            add(btn_add, c);
            c.weightx = 1;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 5;
            panel_table.add(new JScrollPane(tbl_usedEvents), BorderLayout.CENTER);
            panel_right.setLayout(new BoxLayout(panel_right, BoxLayout.Y_AXIS));
            panel_right.add(btn_delete);
            panel_table.add(panel_right, BorderLayout.EAST);
            add(panel_table, c);
        }

        private void addData() {
            service = Lookup.getDefault().lookup(EventService.class);
            eventList = service.findAll();
            List<String> events = new ArrayList<>();
            for (Event e : eventList) {
                events.add(e.getName());
            }
            cbox_availableEvents.setModel(new DefaultComboBoxModel(events.toArray()));
            tblmdl_eventsModel.setEvents((List<Event>) getValue());
        }

        private void enableButtons() {
            btn_add.setEnabled(!eventList.isEmpty());
            btn_delete.setEnabled(tblmdl_eventsModel.getRowCount() > 0);
        }

        private void addActionListeners() {
            btn_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Event selected = service.findById(eventList.get(cbox_availableEvents.getSelectedIndex()).getId());
                    selected.setId(null);
                    selected.setDependency(ExecutionState.INDEPENDENT);
                    tblmdl_eventsModel.addEvent(selected);
                    btn_delete.setEnabled(true);
                }
            });

            btn_delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tbl_usedEvents.getSelectedRow() >= 0) {
                        tblmdl_eventsModel.removeEventByID(tbl_usedEvents.getSelectedRow());
                        if (tbl_usedEvents.getRowCount() < 1) {
                            btn_delete.setEnabled(false);
                        }
                    }
                }
            });

            cbox_executionState.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(cbox_executionState)) {
                        List<Event> events = tblmdl_eventsModel.getEvents();
                        JComboBox<ExecutionState> source = (JComboBox) e.getSource();
                        if (tbl_usedEvents.getSelectedRow() >= 0 && tbl_usedEvents.getSelectedRow() < tblmdl_eventsModel.getRowCount()) {
                            events.get(tbl_usedEvents.getSelectedRow()).setDependency((ExecutionState) source.getSelectedItem());
                            tblmdl_eventsModel.setEvents(events);
                        }
                    }
                }
            });
        }

        private class EventTableModel extends AbstractTableModel {

            private List<Event> events;

            public EventTableModel() {
                this.events = new ArrayList<>();
            }

            public List<Event> getEvents() {
                return this.events;
            }

            public void setEvents(List<Event> events) {
                this.events = events;
                fireTableDataChanged(true);
            }

            public void addEvent(Event e) {
                events.add(e);
                fireTableDataChanged(true);
            }

            public void removeEventByID(int i) {
                if (i >= 0 && i < events.size()) {
                    events.remove(i);
                    fireTableDataChanged(true);
                }
            }

            private void setNewValues() {
                ArrayList<Event> newList = new ArrayList<>();
                for (Event e : events) {
                    Event newEvent = new Event();
                    newEvent.setDependency(e.getDependency());
                    newEvent.setDescription(e.getDescription());
                    newEvent.setEvents(e.getEvents());
                    newEvent.setExecutionState(e.getExecutionState());
                    newEvent.setId(e.getId());
                    newEvent.setLastUpdate(e.getLastUpdate());
                    newEvent.setName(e.getName());
                    newEvent.setParent(e.getParent());
                    newEvent.setProbability(e.getProbability());
                    newEvent.setProcedures(e.getProcedures());
                    newEvent.setUniqueIdentifier(e.getUniqueIdentifier());
                    newList.add(newEvent);
                }
                setValue(newList);
                events = newList;
            }

            @Override
            public int getRowCount() {
                return events.size();
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
                if (columnIndex == 1) {
                    return String.class;
                }
                return JComboBox.class;
            }

            @Override
            public String getColumnName(int column) {
                return column == 0 ? bundle.getString("EVENT") : bundle.getString("DEPENDENCY");
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (rowIndex < events.size() && rowIndex >= 0) {
                    Event e = this.events.get(rowIndex);
                    if (e == null || e.getDependency() == null) {
                        return null;
                    }
                    return columnIndex == 0 ? e.getName() : e.getDependency();
                }
                return null;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 1:
                        this.events.get(rowIndex).setDependency((ExecutionState) aValue);
                }
                setNewValues();
            }

            public final void fireTableDataChanged(boolean souldSetValue) {
                super.fireTableDataChanged();
                if (souldSetValue) {
                    setNewValues();
                }
            }
        }
    }
}
