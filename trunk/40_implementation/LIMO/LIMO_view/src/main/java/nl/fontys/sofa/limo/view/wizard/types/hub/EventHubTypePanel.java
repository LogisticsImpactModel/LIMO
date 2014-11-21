/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;

public final class EventHubTypePanel extends EventsPanel {

    private HubType ht;

    public EventHubTypePanel() {
        setLegView();
    }

    @Override
    public String getName() {
        return "Events";
    }

    /**
     * Removes the dependency column which is just needed by events.
     */
    public void setLegView() {
        TableColumn tcol = eventsTable.getColumnModel().getColumn(1);
        eventsTable.getColumnModel().removeColumn(tcol);
    }

    @Override
    protected void setAddButtonListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(allEvents.get(eventsCheckbox.getSelectedIndex()).getId());
                selected.setId(null);
  //              selected.setParent(ht);
                selected.setDependency(ExecutionState.INDEPENDENT);
                eventsTableModel.getEvents().add(selected);
                eventsTableModel.fireTableDataChanged();
                deleteButton.setEnabled(true);
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
        eventsCheckbox.setModel(new DefaultComboBoxModel(events.toArray()));
    }

    public void update(HubType ht) {
        this.ht = ht;
        update(ht.getEvents());
    }
}
