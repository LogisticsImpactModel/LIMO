/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Matthias Brück
 */
public class DragNDropTableModel implements TableModel{
    
    private final String columnNames[];
    private final Object[][] values;
    private final Class[] classes;

    public DragNDropTableModel(String[] columnNames, Object[][] values, Class[] classes){
        this.columnNames = columnNames;
        this.values = values;
        this.classes = classes;
    }
    
    @Override
    public int getRowCount() {
        return values.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        values[rowIndex][columnIndex] = aValue;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
