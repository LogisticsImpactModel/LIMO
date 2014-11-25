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
            List<Event> usedEvents = (List<Event>) getValue();
            eventsTableModel.setEvents(usedEvents);
            eventsTableModel.fireTableDataChanged();
            setTableAndCheckbox();
        }

        private void setTableAndCheckbox() {
            ArrayList<String> allEventsName = new ArrayList<>();
            List<Event> usedEvents = new ArrayList<>(eventsTableModel.getEvents());
            if (allEvents != null) {
                for (Event event : allEvents) {
                    boolean valid = true;
                    for (Event used : usedEvents) {
                        if (event.getName() != null && used.getName() != null) {
                            valid = !event.getName().equals(used.getName());
                        }
                        if (!valid) {
                            break;
                        }
                    }
                    if (valid) {
                        allEventsName.add(event.getName());
                    }
                }
                addButton.setEnabled(!allEvents.isEmpty());
                eventsCheckbox.setModel(new DefaultComboBoxModel(allEventsName.toArray()));
            } else {
                allEvents = new ArrayList<>();
                eventsCheckbox.setModel(new DefaultComboBoxModel(new String[]{}));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(addButton)) {
                if (eventsCheckbox.getSelectedIndex() >= 0 && eventsCheckbox.getSelectedIndex() < eventsCheckbox.getItemCount()) {
                    Event selected = null;
                    for (int i = 0; i < allEvents.size(); i++) {
                        if (((String) eventsCheckbox.getSelectedItem()).equals(allEvents.get(i).getName())) {
                            selected = service.findById(allEvents.get(i).getId());
                            break;
                        }
                    }
                    if (selected != null) {
                        List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                        selected.setId(null);
                        selected.setDependency(ExecutionState.INDEPENDENT);
                        events.add(selected);

                        eventsTableModel.setEvents(events);
                        eventsTableModel.fireTableDataChanged();

                        setTableAndCheckbox();
                        setValue(events);
                        checkButtonStates();
                    }
                }
            } else if (e.getSource().equals(deleteButton)) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTableModel.getRowCount()) {
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    Event eventToRemove = events.get(eventsTable.getSelectedRow());
                    events.remove(eventToRemove);

                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();

                    setTableAndCheckbox();
                    setValue(events);
                    checkButtonStates();
                }
            }
        }

        private void checkButtonStates() {
            deleteButton.setEnabled(eventsTableModel.getRowCount() > 0);
            addButton.setEnabled(eventsCheckbox.getItemCount() > 0);
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTable.getRowCount()) {
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    events.get(eventsTable.getSelectedRow()).setExecutionState((ExecutionState) executionStateCheckbox.getSelectedItem());

                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();

                    setTableAndCheckbox();
                    setValue(events);
                }
            }
        }
    }
}
