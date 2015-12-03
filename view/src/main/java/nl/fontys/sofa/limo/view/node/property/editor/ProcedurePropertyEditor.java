package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.view.custom.procedure.ProcedureComponent;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This class implements property editor for procedures.
 *
 * @author Matthias Brück
 */
public class ProcedurePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<Procedure> procedures = (List<Procedure>) getValue();
        if (procedures == null || procedures.isEmpty()) {
            return LIMOResourceBundle.getString("PROCEDURES");
        }

        StringBuilder name = new StringBuilder();
        procedures.stream().forEach((prod) -> {
            if (name.toString().length() == 0) {
                name.append(prod.getName());
            } else {
                name.append(" - ").append(prod.getName());
            }
        });
        return name.toString();
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

    /**
     * This is the custom editor for editing the procedure property.
     */
    private class CustomEditor extends ProcedureComponent implements ItemListener, CellEditorListener {

        private final JTextField name;

        /**
         * Creates a new instance of this custom editor.
         */
        public CustomEditor() {
            super();
            List<Procedure> procedures = (List<Procedure>) getValue();
            if (procedures != null) {
                setProcedureTable(procedures);
            }
            procedureCategoryCheckbox.addItemListener(this);
            timeTypesCheckbox.addItemListener(this);

            name = new JTextField();
            DefaultCellEditor nameEditor = new DefaultCellEditor(name);
            nameEditor.addCellEditorListener(this);
            table.getColumnModel().getColumn(0).setCellEditor(nameEditor);
            setProcedureComboBox();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(deleteButton)) {
                int rowToDelete = table.getSelectedRow();
                if (rowToDelete > -1 && rowToDelete < getActiveTableState().size()) {
                    deleteProcedure(rowToDelete);
                    List<Procedure> procedures = getActiveTableState();
                    setValue(procedures);
                    checkButtonsState();
                    setProcedureComboBox();
                }
            } else if (e.getSource().equals(newButton)) {
                addProcedure();
                List<Procedure> procedures = getActiveTableState();
                setValue(procedures);
                checkButtonsState();
                setProcedureComboBox();
            } else if (e.getSource().equals(addButton)) {
                addClicked();
                setProcedureComboBox();
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
                    } else if (e.getSource().equals(timeTypesCheckbox)) {
                        procedures.get(table.getSelectedRow()).setTimeType((TimeType) timeTypesCheckbox.getSelectedItem());
                    }
                    setProcedureTable(procedures);
                    setValue(procedures);
                    procedureCategoryCheckbox.addItemListener(this);
                    timeTypesCheckbox.addItemListener(this);
                }
            }
        }

        @Override
        public void editingStopped(ChangeEvent e) {
            List<Procedure> procedures = getActiveTableState();
            setValue(procedures);
        }

        @Override
        public void editingCanceled(ChangeEvent e) {
            /**
             * not used*
             */
        }
        
        private void setProcedureComboBox() {
            ArrayList<String> allProcedureNames = new ArrayList<>();
            List<String> usedProcedures = new ArrayList<>();
            for (int row = 0; row < table.getRowCount(); row++) {
                usedProcedures.add((String)table.getValueAt(row, 0));
            }
            if (allProcedures != null) {
                for (Procedure procedure : allProcedures) {
                    boolean valid = true;
                    for (String used : usedProcedures) {
                        if (procedure.getName() != null && used != null) {
                            valid = !procedure.getName().equals(used);
                        }
                        if (!valid) {
                            break;
                        }
                    }
                    if (valid) {
                        allProcedureNames.add(procedure.getName());
                    }
                }
                addButton.setEnabled(!allProcedures.isEmpty());
                proceduresComboBox.setModel(new DefaultComboBoxModel(allProcedureNames.toArray()));
            } else {
                allProcedures = new ArrayList<>();
                proceduresComboBox.setModel(new DefaultComboBoxModel(new String[]{}));
            }
        }

        private void addClicked() {
            Procedure selected = null;
            for (Procedure procedure : allProcedures) {
                if (((String) proceduresComboBox.getSelectedItem()).equals(procedure.getName())) {
                    selected = service.findById(procedure.getId());
                    break;
                }
            }
            if (selected != null) {
                List<Procedure> procedures = new ArrayList<>(tableProcedures);
                selected.setId(null);
                procedures.add(selected);
                model.fireTableDataChanged();
                setProcedureTable(procedures);
                setValue(procedures);
                checkButtonsState();
            }
        }
    }
}
