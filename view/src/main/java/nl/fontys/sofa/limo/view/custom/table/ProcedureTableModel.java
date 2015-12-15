/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 *
 * @author nilsh
 */
public class ProcedureTableModel extends AbstractTableModel {

    private List<Procedure> procedures;

    /**
     * Create a new empty table model.
     */
    public ProcedureTableModel() {
        this(new ArrayList<>());
    }

    /**
     * Create a table model based on already existing events.
     *
     * @param events to be set.
     */
    public ProcedureTableModel(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<Procedure> getProcedures() {
        if (this.procedures == null) {
            this.procedures = new ArrayList<>();
        }
        return this.procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
        this.fireTableDataChanged();
    }

    public void removeProcedue(int pos) {
        procedures.remove(pos);
        fireTableRowsDeleted(pos, pos);
    }

    @Override
    public int getRowCount() {
        return this.procedures.size();
    }

    // model = new DragNDropTableModel(,
    //         valueList, new Class[]{String.class, String.class, Value.class, TimeType.class, Value.class, Value.
    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    private static final Class[] COLUMNCLASS = new Class[]{String.class, String.class, Value.class, TimeType.class, Value.class, Value.class};
    private static final String[] COLUMNNAMES = new String[]{
        LIMOResourceBundle.getString("PROCEDURE"), LIMOResourceBundle.getString("CATEGORY"), LIMOResourceBundle.getString("TIME_COST"), LIMOResourceBundle.getString("TIME_TYPE"), LIMOResourceBundle.getString("MONEY_COST"), LIMOResourceBundle.getString("CO2")
    };

    @Override

    public Class<?> getColumnClass(int columnIndex) {
        return COLUMNCLASS[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNNAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Procedure p = this.procedures.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getName();
            case 1:
                return p.getCategory();
            case 2:
                return p.getTime();
            case 3:
                return p.getTimeType();
            case 4:
                return p.getCost();
            case 5:
                return p.getCotwo();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Procedure p = this.procedures.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                p.setName((String) aValue);
                return;
            }
            case 1: {
                p.setCategory((String) aValue);
                return;
            }
            case 2: {
                p.setTime((Value) aValue);
                return;
            }
            case 3: {
                p.setTimeType((TimeType) aValue);
                return;
            }
            case 4: {
                p.setCost((Value) aValue);
                return;
            }
            case 5: {
                p.setCotwo((Value) aValue);
                return;
            }
        }
    }

}
