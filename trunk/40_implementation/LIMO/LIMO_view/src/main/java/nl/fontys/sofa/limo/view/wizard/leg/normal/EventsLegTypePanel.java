/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg.normal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;

public final class EventsLegTypePanel extends EventsPanel {

    private Leg leg;

    public EventsLegTypePanel() {
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
        TableColumn tcol = tbl_usedEvents.getColumnModel().getColumn(1);
        tbl_usedEvents.getColumnModel().removeColumn(tcol);
    }

    @Override
    protected void setAddButtonListener() {
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event selected = service.findById(allEvents.get(cbox_addEvent.getSelectedIndex()).getId());
                selected.setId(null);
                selected.setParent(leg);
                selected.setDependency(ExecutionState.INDEPENDENT);
                tblmdl_usedEvents.getEvents().add(selected);
                tblmdl_usedEvents.fireTableDataChanged();
                btn_delete.setEnabled(true);
            }
        });
    }

    @Override
    protected void setTableModel() {
        List<String> events = new ArrayList<>();
        btn_add.setEnabled(!allEvents.isEmpty());
        for (Event e : allEvents) {
            events.add(e.getName());
        }
        cbox_addEvent.setModel(new DefaultComboBoxModel(events.toArray()));
    }

    public void update(Leg leg) {
        this.leg = leg;
        update(leg.getEvents());
    }
}
