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
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;

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
            for (ActionListener listener : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(listener);
            }
            deleteButton.addActionListener(this);
            executionStateCheckbox.addItemListener(this);
            deleteButton.setEnabled(!eventsTableModel.getEvents().isEmpty());
        }

        @Override
        protected void setAddButtonListener() {
            addButton.addActionListener(this);
        }

        @Override
        protected void setTableModel() {
            if (allEvents != null) {
                ArrayList<String> allEventsName = new ArrayList<>();
                for (Event event : allEvents) {
                    allEventsName.add(event.getName());
                }
                addButton.setEnabled(!allEvents.isEmpty());
                eventsCheckbox.setModel(new DefaultComboBoxModel(allEventsName.toArray()));
            } else {
                eventsCheckbox.setModel(new DefaultComboBoxModel(new String[]{}));
            }
            List<Event> usedEvents = (List<Event>) getValue();
            eventsTableModel.setEvents(usedEvents);
            eventsTableModel.fireTableDataChanged();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(addButton)) {
                if (eventsCheckbox.getSelectedIndex() >= 0 && eventsCheckbox.getSelectedIndex() < eventsCheckbox.getItemCount()) {
                    Event selected = service.findById(allEvents.get(eventsCheckbox.getSelectedIndex()).getId());
                    if (selected != null) {
                        List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                        selected.setId(null);
                        selected.setDependency(ExecutionState.INDEPENDENT);
                        events.add(selected);
                        eventsTableModel.setEvents(events);
                        eventsTableModel.fireTableDataChanged();
                        setValue(events);
                        deleteButton.setEnabled(true);
                    }
                }
            } else if (e.getSource().equals(deleteButton)) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTableModel.getRowCount()) {
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    events.remove(eventsTable.getSelectedRow());
                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();
                    setValue(events);
                    deleteButton.setEnabled(!eventsTableModel.getEvents().isEmpty());
                }
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTableModel.getRowCount()) {
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    events.get(eventsTable.getSelectedRow()).setExecutionState((ExecutionState) executionStateCheckbox.getSelectedItem());
                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();
                    setValue(new ArrayList<>(eventsTableModel.getEvents()));
                }
            }
        }
    }
}
