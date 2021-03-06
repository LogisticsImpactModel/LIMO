package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * Panel which extends the EventsPanel for the adding of events. The added
 * events are sub events of the event which should be created or edited.
 *
 * @author Sven Mäurer
 */
public class SubEventsPanel extends EventsPanel {

    private Event event;

    public SubEventsPanel() {
        super();
        List<String> events = new ArrayList<>();
        addButton.setEnabled(!allEvents.isEmpty());
        allEvents.stream().filter((e) -> (event == null || (event != null && !e.getId().equals(event.getId())))).forEach((e) -> {
            events.add(e.getName());
        });
        eventsComboBoxModel = new DefaultComboBoxModel(events.toArray());
        eventsComboBox.setModel(eventsComboBoxModel);
    }

    /**
     * Retrieve the selected event from the combo box and add it to the table.
     */
    @Override
    protected void setAddButtonListener() {
        addButton.addActionListener((ActionEvent e) -> {
            Event selected = null;
            for (Event allEvent : allEvents) {
                if (((String) eventsComboBox.getSelectedItem()).equals(allEvent.getName())) {
                    selected = service.findById(allEvent.getId());
                    break;
                }
            }
            if (selected != null) {
                selected.setId(null);
                selected.setDependency(ExecutionState.INDEPENDENT);
                eventsTableModel.getEvents().add(selected);
                eventsTableModel.fireTableDataChanged();
                setTableAndCheckbox();
                checkButtonsState();
            }
        });
        deleteButton.addActionListener((ActionEvent e) -> {
            setTableAndCheckbox();
        });
    }
    
    @Override
    protected void setTableModel() {

    }

    public void update(Event event) {
        this.event = event;
        update(event.getEvents());
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("SUB_EVENTS");
    }

}
