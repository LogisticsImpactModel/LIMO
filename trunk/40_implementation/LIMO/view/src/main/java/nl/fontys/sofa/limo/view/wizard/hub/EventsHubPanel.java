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
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;

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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(allEvents.get(eventsComboBox.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setParent(hub);
                selected.setDependency(ExecutionState.INDEPENDENT);
                eventsTableModel.getEvents().add(selected);
                eventsTableModel.fireTableDataChanged();
                deleteButton.setEnabled(true);
                //eventsComboBox.removeItemAt(eventsComboBox.getSelectedIndex());
                checkAddButtonState();
                checkDeleteButtonState();
            }
        });
    }

    @Override
    protected void setTableModel() {
        List<String> events = new ArrayList<>();
        addButton.setEnabled(!allEvents.isEmpty());
        for (Event e : allEvents) {
            events.add(e.getName());
        }
        eventsComboBox.setModel(new DefaultComboBoxModel(events.toArray()));
    }
}
