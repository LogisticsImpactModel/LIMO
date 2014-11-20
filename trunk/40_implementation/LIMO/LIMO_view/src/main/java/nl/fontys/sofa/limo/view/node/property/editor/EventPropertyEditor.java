package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.pane.EventsPanel;

/**
 *
 * @author Matthias BrÃƒÆ’Ã‚Â¼ck
 */
public class EventPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<Event> events = (List<Event>) getValue();
        if (events == null) {
            return "Number of Events: 0";
        }
        return "Number of Events: " + events.size();
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

    private class CustomEditor extends EventsPanel implements ActionListener, ItemListener {

        public CustomEditor() {
            super();
            for (ActionListener listener : btn_delete.getActionListeners()) {
                btn_delete.removeActionListener(listener);
            }
            btn_delete.addActionListener(this);
            cbox_executionState.addItemListener(this);
            btn_delete.setEnabled(!tblmdl_usedEvents.getEvents().isEmpty());
        }

        @Override
        protected void setAddButtonListener() {
            btn_add.addActionListener(this);
        }

        @Override
        protected void setTableModel() {
            if (allEvents != null) {
                ArrayList<String> allEventsName = new ArrayList<>();
                for (Event event : allEvents) {
                    allEventsName.add(event.getName());
                }
                btn_add.setEnabled(!allEvents.isEmpty());
                cbox_addEvent.setModel(new DefaultComboBoxModel(allEventsName.toArray()));
            } else {
                cbox_addEvent.setModel(new DefaultComboBoxModel(new String[]{}));
            }
            List<Event> usedEvents = (List<Event>) getValue();
            tblmdl_usedEvents.setEvents(usedEvents);
            tblmdl_usedEvents.fireTableDataChanged();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btn_add)) {
                if (cbox_addEvent.getSelectedIndex() >= 0 && cbox_addEvent.getSelectedIndex() < cbox_addEvent.getItemCount()) {
                    Event selected = service.findById(allEvents.get(cbox_addEvent.getSelectedIndex()).getId());
                    if (selected != null) {
                        List<Event> events = new ArrayList<>(tblmdl_usedEvents.getEvents());
                        selected.setId(null);
                        selected.setDependency(ExecutionState.INDEPENDENT);
                        events.add(selected);
                        tblmdl_usedEvents.setEvents(events);
                        tblmdl_usedEvents.fireTableDataChanged();
                        setValue(events);
                        btn_delete.setEnabled(true);
                    }
                }
            } else if (e.getSource().equals(btn_delete)) {
                if (tbl_usedEvents.getSelectedRow() >= 0 && tbl_usedEvents.getSelectedRow() < tblmdl_usedEvents.getRowCount()) {
                    List<Event> events = new ArrayList<>(tblmdl_usedEvents.getEvents());
                    events.remove(tbl_usedEvents.getSelectedRow());
                    tblmdl_usedEvents.setEvents(events);
                    tblmdl_usedEvents.fireTableDataChanged();
                    setValue(events);
                    btn_delete.setEnabled(!tblmdl_usedEvents.getEvents().isEmpty());
                }
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (tbl_usedEvents.getSelectedRow() >= 0 && tbl_usedEvents.getSelectedRow() < tblmdl_usedEvents.getRowCount()) {
                    List<Event> events = new ArrayList<>(tblmdl_usedEvents.getEvents());
                    events.get(tbl_usedEvents.getSelectedRow()).setExecutionState((ExecutionState) cbox_executionState.getSelectedItem());
                    tblmdl_usedEvents.setEvents(events);
                    tblmdl_usedEvents.fireTableDataChanged();
                    setValue(new ArrayList<>(tblmdl_usedEvents.getEvents()));
                }
            }
        }
    }
}
