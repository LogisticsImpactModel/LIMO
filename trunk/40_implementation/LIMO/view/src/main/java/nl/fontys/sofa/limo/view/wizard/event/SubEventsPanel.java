package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

public class SubEventsPanel extends EventsPanel {

    private Event event;

    @Override
    protected void setAddButtonListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(allEvents.get(eventsComboBox.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setDependency(ExecutionState.INDEPENDENT);
                eventsTableModel.getEvents().add(selected);
                eventsTableModel.fireTableDataChanged();
                checkDeleteButtonState();
            }
        });
    }

    @Override
    protected void setTableModel() {
        List<String> events = new ArrayList<>();
        addButton.setEnabled(!allEvents.isEmpty());
        for (Event e : allEvents) {
            if (event == null || (event != null && !e.getId().equals(event.getId()))) {
                events.add(e.getName());
            }
        }
        eventsComboBoxModel = new DefaultComboBoxModel(events.toArray());
        eventsComboBox.setModel(eventsComboBoxModel);
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
