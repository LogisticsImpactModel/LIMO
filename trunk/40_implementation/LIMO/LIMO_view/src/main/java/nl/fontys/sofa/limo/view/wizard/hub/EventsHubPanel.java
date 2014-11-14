package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.custom.pane.EventsPanel;

public final class EventsHubPanel extends EventsPanel {

    private Hub hub;

    public EventsHubPanel() {
        setHubView();
    }

    @Override
    public String getName() {
        return "Events";
    }

    /**
     * Removes the dependency column which is just needed by events.
     */
    public void setHubView() {
        TableColumn tcol = eventsTable.getColumnModel().getColumn(1);
        eventsTable.getColumnModel().removeColumn(tcol);
    }

    @Override
    protected void setAddButtonListener() {
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(eventList.get(cbEvents.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setParent(hub);
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
        for (Event e : eventList) {
            events.add(e.getName());
        }
        cbEvents.setModel(new DefaultComboBoxModel(events.toArray()));
    }

    public void update(Hub hub) {
        this.hub = hub;
        update(hub.getEvents());
    }

}