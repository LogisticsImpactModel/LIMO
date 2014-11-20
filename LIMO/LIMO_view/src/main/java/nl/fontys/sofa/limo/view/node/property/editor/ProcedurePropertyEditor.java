package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyEditorSupport;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.view.custom.ProcedureComponent;

/**
 *
 * @author Matthias Brück
 */
public class ProcedurePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<Procedure> procedures = (List<Procedure>) getValue();
        if (procedures == null) {
            return "Number of Procedures: 0";
        }
        return "Number of Procedures: " + procedures.size();
    }

    @Override
    public void setAsText(String s) {
    }

    @Override
    public Component getCustomEditor() {
        return new CustomEditor();
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    private class CustomEditor extends ProcedureComponent implements ItemListener {

        public CustomEditor() {
            super();
            List<Procedure> procedures = (List<Procedure>) getValue();
            if (procedures != null) {
                setProcedureTable(procedures);
            }

            procedureCategoryCheckbox.addItemListener(this);
            directionCheckbox.addItemListener(this);
            timeTypesCheckbox.addItemListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(deleteButton)) {
                int rowToDelete = table.getSelectedRow();
                if (rowToDelete > -1 && rowToDelete < getActiveTableState().size()) {
                    deleteProcedure(rowToDelete);
                    List<Procedure> procedures = getActiveTableState();
                    setValue(procedures);
                }
            } else if (e.getSource().equals(addButton)) {
                addProcedure();
                List<Procedure> procedures = getActiveTableState();
                setValue(procedures);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(table)) {
                if (e.getClickCount() > 1) {
                    editProcedure();
                    List<Procedure> procedures = getActiveTableState();
                    setValue(procedures);
                }
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (table.getSelectedRow() >= 0 && table.getSelectedRow() < table.getModel().getRowCount()) {
                    List<Procedure> procedures = getActiveTableState();
                    if (e.getSource().equals(procedureCategoryCheckbox)) {
                        procedures.get(table.getSelectedRow()).setCategory((String) procedureCategoryCheckbox.getSelectedItem().toString());
                    } else if (e.getSource().equals(directionCheckbox)) {
                        procedures.get(table.getSelectedRow()).setDirection((ProcedureResponsibilityDirection) directionCheckbox.getSelectedItem());
                    } else if (e.getSource().equals(timeTypesCheckbox)) {
                        procedures.get(table.getSelectedRow()).setTimeType((TimeType) timeTypesCheckbox.getSelectedItem());
                    }
                    setProcedureTable(procedures);
                    setValue(procedures);
                    procedureCategoryCheckbox.addItemListener(this);
                    directionCheckbox.addItemListener(this);
                    timeTypesCheckbox.addItemListener(this);
                }
            }
        }
    }
}
