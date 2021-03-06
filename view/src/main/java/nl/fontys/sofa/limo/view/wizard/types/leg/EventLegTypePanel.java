package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.custom.panel.EventsPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * Add Event to LegType Panel
 *
 * @author Pascal Lindner
 */
public final class EventLegTypePanel extends EventsPanel {

    public EventLegTypePanel() {
        setLegView();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("EVENTS");
    }

    /**
     * Removes the dependency column which is just needed by events.
     */
    public void setLegView() {
        TableColumn tcol = eventsTable.getColumnModel().getColumn(1);
        eventsTable.getColumnModel().removeColumn(tcol);
    }

    //Add Listener
    @Override
    protected void setAddButtonListener() {
        addButton.addActionListener((ActionEvent e) -> {
            Event selected = service.findById(allEvents.get(eventsComboBox.getSelectedIndex()).getId());
            selected.setId(null);
            //             selected.setParent(lt);
            selected.setDependency(ExecutionState.INDEPENDENT);
            eventsTableModel.getEvents().add(selected);
            eventsTableModel.fireTableDataChanged();
            deleteButton.setEnabled(true);
        });
    }

    @Override
    protected void setTableModel() {
        List<String> events = new ArrayList<>();
        addButton.setEnabled(!allEvents.isEmpty());
        allEvents.stream().forEach((e) -> {
            events.add(e.getName());
        });
        eventsComboBox.setModel(new DefaultComboBoxModel(events.toArray()));
    }
}
