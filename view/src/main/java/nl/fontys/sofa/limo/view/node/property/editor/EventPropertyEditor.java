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
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.DefaultFinishClickHandler;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.FinishClickHandler;
import org.openide.WizardDescriptor;

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
                getNewEventByWizard();
            });
        }

        private Event getNewEventByWizard() {
            EventWizardAction action = new EventWizardAction();
            FinishClickHandler handler = action.new DefaultFinishClickHandler() {
                @Override
                public void handle(Event event, WizardDescriptor descriptor) {
                    super.handle(event, descriptor);
                    List<Event> events = new ArrayList<>(eventsTableModel.getEvents());
                    event.setId(null);
                    event.setDependency(ExecutionState.INDEPENDENT);
                    events.add(event);
                    eventsTableModel.setEvents(events);
                    eventsTableModel.fireTableDataChanged();
                    setTableAndCheckbox();
                    setValue(events);
                    checkButtonsState();
                }
            };
            action.setFinishClickHandler(handler);
            action.actionPerformed(new ActionEvent(newButton, 0, ""));
            return null;
        }
    }
}
