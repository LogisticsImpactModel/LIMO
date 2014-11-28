package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTable;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;

public class AddProcedureDialog extends JDialog implements ActionListener {

    private final JButton saveButton, cancelButton, addTimeButton, addCostButton;
    private final JTextField nameTextField, costTextField, timeTextField;
    private final JComboBox timeTypeCombobox, directionCombobox, categoryCombobox;
    private Value timeValue, costValue;
    private Procedure newProcedure;
    private final DragNDropTable table;

    public AddProcedureDialog(ProcedureCategoryDAO procedureCategoryDao, DragNDropTable dragNDropTable) {
        this.table = dragNDropTable;
        //LAYOUT
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout("5px, pref, 5px, pref, pref:grow, 5px, pref, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        //COMPONENTS
        nameTextField = new JTextField();
        categoryCombobox = new JComboBox(procedureCategoryDao.findAll().toArray());
        timeTypeCombobox = new JComboBox(TimeType.values());
        timeValue = new SingleValue(0.0);
        timeTextField = new JTextField(timeValue.toString());
        timeTextField.setEditable(false);
        addTimeButton = new JButton("...");
        costValue = new SingleValue(0.0);
        costTextField = new JTextField(costValue.toString());
        costTextField.setEditable(false);
        addCostButton = new JButton("...");
        directionCombobox = new JComboBox(ProcedureResponsibilityDirection.values());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        //ADD COMPONENTS
        this.add(new JLabel("Name:"), cc.xy(2, 2));
        this.add(nameTextField, cc.xyw(4, 2, 2));
        this.add(new JLabel("Category:"), cc.xy(2, 4));
        this.add(categoryCombobox, cc.xyw(4, 4, 2));
        this.add(new JLabel("Time Type:"), cc.xy(2, 6));
        this.add(timeTypeCombobox, cc.xyw(4, 6, 2));
        this.add(new JLabel("Time Cost:"), cc.xy(2, 8));
        this.add(timeTextField, cc.xyw(4, 8, 2));
        this.add(addTimeButton, cc.xy(7, 8));
        this.add(new JLabel("Money Cost:"), cc.xy(2, 10));
        this.add(costTextField, cc.xyw(4, 10, 2));
        this.add(addCostButton, cc.xy(7, 10));
        this.add(new JLabel("Direction:"), cc.xy(2, 12));
        this.add(directionCombobox, cc.xyw(4, 12, 2));
        this.add(saveButton, cc.xy(2, 14));
        this.add(cancelButton, cc.xy(4, 14));
        //ADD COMPONENTS TO LISTENER
        cancelButton.addActionListener(this);
        saveButton.addActionListener(this);
        addCostButton.addActionListener(this);
        addTimeButton.addActionListener(this);
        //DIALOG OPTIONS
        this.setSize(250, 300);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setTitle("Procedures");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private boolean isValidProcedure() {
        return !(nameTextField.getText().equals("") || nameTextField.getText().equals("") || costTextField.getText().equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addCostButton)) {
            new EditValueDialog(costValue, new EditValueDialogListener() {

                @Override
                public void newValue(Value changedValue) {
                    if (costValue != null) {
                        costValue = changedValue;
                        costTextField.setText(costValue.toString());
                        AddProcedureDialog.this.revalidate();
                        AddProcedureDialog.this.repaint();
                    }
                }
            });
        }
        if (e.getSource().equals(addTimeButton)) {
            new EditValueDialog(timeValue, new EditValueDialogListener() {

                @Override
                public void newValue(Value changedValue) {
                    if (timeValue != null) {
                        timeValue = changedValue;
                        timeTextField.setText(timeValue.toString());
                        AddProcedureDialog.this.revalidate();
                        AddProcedureDialog.this.repaint();
                    }
                }
            });
        }
        if (e.getSource().equals(cancelButton)) {
            this.dispose();
        }
        if (e.getSource().equals(saveButton)) {
            if (isValidProcedure()) {
                String name = nameTextField.getText();
                String category = "";
                try {
                    category = categoryCombobox.getSelectedItem().toString();
                } catch (Exception ex) {
                }
                TimeType timeType = (TimeType) timeTypeCombobox.getSelectedItem();
                ProcedureResponsibilityDirection direction = (ProcedureResponsibilityDirection) directionCombobox.getSelectedItem();
                newProcedure = new Procedure(name, category, costValue, timeValue, timeType, direction);
                List<Object> newRow = new ArrayList<>();
                newRow.add(newProcedure.getName());
                newRow.add(newProcedure.getCategory());
                newRow.add(newProcedure.getTimeType());
                newRow.add(newProcedure.getTime());
                newRow.add(newProcedure.getCost());
                newRow.add(newProcedure.getDirection());
                ((DragNDropTableModel) table.getModel()).addRow(newRow);
                ((DragNDropTableModel) table.getModel()).fireTableDataChanged();
                table.revalidate();
                table.repaint();
                this.dispose();
            }
        }
    }

    public interface EditValueDialogListener {

        void newValue(Value value);
    }
}
