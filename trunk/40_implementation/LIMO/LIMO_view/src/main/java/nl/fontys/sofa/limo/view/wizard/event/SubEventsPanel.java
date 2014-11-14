package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.pane.EventsPanel;

public class SubEventsPanel extends EventsPanel {
    
    private Event event;
    
    @Override
    protected void setAddButtonListener() {
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(eventList.get(cbEvents.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setParent(event);
                selected.setDependency(ExecutionState.INDEPENDENT);
                tableModel.getEvents().add(selected);
                tableModel.fireTableDataChanged();
                btnDelete.setEnabled(true);
            }
        });
    }
    
    @Override
    protected void setTableModel() {
        List<String> events = new ArrayList<>();
        btnAdd.setEnabled(!eventList.isEmpty());
        cbEvents.setModel(new DefaultComboBoxModel(events.toArray()));
        for (Event e : eventList) {
            if (event == null || (event != null && !e.getId().equals(event.getId()))) {
                events.add(e.getName());
            }
        }
    }
    
    public void update(Event event) {
        this.event = event;
        update(event.getEvents());
    }
    
    @Override
    public String getName() {
        return bundle.getString("SUB_EVENTS");
    }
    
}
