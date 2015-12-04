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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;
import nl.fontys.sofa.limo.view.util.IconUtil;

/**
 * This class is the Property Editor for our events. It enables you to change
 * the events with the same component as in the wizards.
 *
 * @author Matthias Brück
 */
public class EventPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<Event> events = (List<Event>) getValue();
        if (events == null || events.isEmpty()) {
            return "No events";
        }

        StringBuilder name = new StringBuilder();
        events.stream().forEach((event) -> {
            if (name.toString().length() == 0) {
                name.append(event.getName());
            } else {
                name.append(" - ").append(event.getName());
            }
        });
        return name.toString();
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

    /**
     * This CustomEditor is the actual JLabel that gets displayed as Editor.
     */
    private class CustomEditor extends EventsPanel implements ActionListener, ItemListener {

        private JButton newButton;

        public CustomEditor() {
            super();
            for (ActionListener listener : deleteButton.getActionListeners()) {
                deleteButton.removeActionListener(listener);
            }
            deleteButton.addActionListener(this);
            executionStateComboBox.addItemListener(this);
            newButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
            sidebarPanel.add(newButton, 1);
            setNewButtonListener();
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

        /**
         * Sets the table, combobox and checkboxes of the used and unused items.
         */
        private void setTableAndCheckbox() {
            ArrayList<String> allEventsName = new ArrayList<>();
            List<Event> usedEvents;
            if (eventsTableModel.getEvents() != null) {
                usedEvents = new ArrayList<>(eventsTableModel.getEvents());
            } else {
                usedEvents = new ArrayList<>();
            }
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
                eventsComboBox.setModel(new DefaultComboBoxModel(allEventsName.toArray()));
            } else {
                allEvents = new ArrayList<>();
                eventsComboBox.setModel(new DefaultComboBoxModel(new String[]{}));
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(addButton)) {
                if (eventsComboBox.getSelectedIndex() >= 0 && eventsComboBox.getSelectedIndex() < eventsComboBox.getItemCount()) {
                    addClicked();
                }
            } else if (e.getSource().equals(deleteButton)) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTableModel.getRowCount()) {
                    deleteClicked();
                }
            }
        }

        /**
         * Handles what should happen when you clicked on the "add" button.
         */
        private void addClicked() {
            Event selected = null;
            for (Event allEvent : allEvents) {
                if (((String) eventsComboBox.getSelectedItem()).equals(allEvent.getName())) {
                    selected = service.findById(allEvent.getId());
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
                checkButtonsState();
            }
        }

        /**
         * Handles what should happen when you clicked on the "delete" button.
         */
        private void deleteClicked() {
            List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
            Event eventToRemove = events.get(eventsTable.getSelectedRow());
            events.remove(eventToRemove);
            eventsTableModel.setEvents(events);
            eventsTableModel.fireTableDataChanged();
            setTableAndCheckbox();
            setValue(events);
            checkButtonsState();
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (eventsTable.getSelectedRow() >= 0 && eventsTable.getSelectedRow() < eventsTable.getRowCount()) {
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    events.get(eventsTable.getSelectedRow()).setExecutionState((ExecutionState) executionStateComboBox.getSelectedItem());

                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();

                    setTableAndCheckbox();
                    setValue(events);
                }
            }
        }

        /**
         * The new action where a new event is created as template and added as well.
         */
        private void setNewButtonListener() {
            newButton.addActionListener((ActionEvent e) -> {
                // TODO: open new event dialog
            });
        }
    }
}
