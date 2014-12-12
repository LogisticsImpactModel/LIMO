package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 *
 * @author Sven Mäurer
 */
public class EventTableModel extends AbstractTableModel {

    private List<Event> events;

    public EventTableModel() {
        this(new ArrayList<Event>());
    }

    public EventTableModel(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        if (this.events == null) {
            this.events = new ArrayList<>();
        }
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return this.events.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return column == 0 ? LIMOResourceBundle.getString("EVENT") : LIMOResourceBundle.getString("DEPENDENCY");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event e = this.events.get(rowIndex);
        if (e == null || e.getDependency() == null) {
            return null;
        }

        return columnIndex == 0 ? e.getName() : e.getDependency().name();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 1:
                this.events.get(rowIndex).setDependency((ExecutionState) aValue);
        }
    }

}
