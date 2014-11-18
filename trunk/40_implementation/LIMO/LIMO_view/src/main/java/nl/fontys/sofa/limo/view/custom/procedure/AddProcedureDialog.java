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
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTable;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;

public class AddProcedureDialog extends JDialog implements ActionListener {

    private final JButton btn_addSave, btn_addCancel, btn_addTime, btn_addCost;
    private final JLabel lbl_name, lbl_category, lbl_timeType, lbl_time, lbl_cost, lbl_direction;
    private final JTextField tf_name, tf_cost, tf_time;
    private final JComboBox cbox_timeType, cbox_direction;
    private JComboBox cbox_category;
    private Value timeValue, costValue;
    private Procedure newProcedure;
    private CellConstraints cc;
    private final DragNDropTable table;

    public AddProcedureDialog(ProcedureCategoryDAO procedureCategoryDao, DragNDropTable table) {
        this.table = table;
        //LAYOUT
        cc = new CellConstraints();
        FormLayout layout = new FormLayout("5px, pref, 5px, pref, pref:grow, 5px, pref, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        this.setLayout(layout);
        //COMPONENTS
        lbl_name = new JLabel("Name:");
        tf_name = new JTextField();
        lbl_category = new JLabel("Category:");
        try {
            cbox_category = new JComboBox(procedureCategoryDao.findAll().toArray());
        } catch (Exception e) {
            cbox_category = new JComboBox(new ProcedureCategory[]{});
        }
        lbl_timeType = new JLabel("Time Type:");
        cbox_timeType = new JComboBox(TimeType.values());
        lbl_time = new JLabel("Time Cost:");
        timeValue = new SingleValue(0.0);
        tf_time = new JTextField(timeValue.toString());
        tf_time.setEditable(false);
        btn_addTime = new JButton("...");
        costValue = new SingleValue(0.0);
        lbl_cost = new JLabel("Money Cost:");
        tf_cost = new JTextField(costValue.toString());
        tf_cost.setEditable(false);
        btn_addCost = new JButton("...");
        lbl_direction = new JLabel("Direction:");
        cbox_direction = new JComboBox(ProcedureResponsibilityDirection.values());
        btn_addSave = new JButton("Save");
        btn_addCancel = new JButton("Cancel");
        //ADD COMPONENTS
        this.add(lbl_name, cc.xy(2, 2));
        this.add(tf_name, cc.xyw(4, 2, 2));
        this.add(lbl_category, cc.xy(2, 4));
        this.add(cbox_category, cc.xyw(4, 4, 2));
        this.add(lbl_timeType, cc.xy(2, 6));
        this.add(cbox_timeType, cc.xyw(4, 6, 2));
        this.add(lbl_time, cc.xy(2, 8));
        this.add(tf_time, cc.xyw(4, 8, 2));
        this.add(btn_addTime, cc.xy(7, 8));
        this.add(lbl_cost, cc.xy(2, 10));
        this.add(tf_cost, cc.xyw(4, 10, 2));
        this.add(btn_addCost, cc.xy(7, 10));
        this.add(lbl_direction, cc.xy(2, 12));
        this.add(cbox_direction, cc.xyw(4, 12, 2));
        this.add(btn_addSave, cc.xy(2, 14));
        this.add(btn_addCancel, cc.xy(4, 14));
        //ADD COMPONENTS TO LISTENER
        btn_addCancel.addActionListener(this);
        btn_addSave.addActionListener(this);
        btn_addCost.addActionListener(this);
        btn_addTime.addActionListener(this);
        //DIALOG OPTIONS
        this.setSize(250, 300);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private boolean isValidProcedure() {
        return !(tf_name.getText().equals("") || tf_name.getText().equals("") || tf_cost.getText().equals(""));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_addCost)) {
            new EditValueDialog(costValue, new EditValueDialogListener() {

                @Override
                public void newValue(Value changedValue) {
                    if (costValue != null) {
                        costValue = changedValue;
                        tf_cost.setText(costValue.toString());
                        AddProcedureDialog.this.revalidate();
                        AddProcedureDialog.this.repaint();
                    }
                }
            });
        }
        if (e.getSource().equals(btn_addTime)) {
            new EditValueDialog(timeValue, new EditValueDialogListener() {

                @Override
                public void newValue(Value changedValue) {
                    if (timeValue != null) {
                        timeValue = changedValue;
                        tf_time.setText(timeValue.toString());
                        AddProcedureDialog.this.revalidate();
                        AddProcedureDialog.this.repaint();
                    }
                }
            });
        }
        if (e.getSource().equals(btn_addCancel)) {
            this.dispose();
        }
        if (e.getSource().equals(btn_addSave)) {
            if (isValidProcedure()) {
                String name = tf_name.getText();
                String category = "";
                try {
                    category = cbox_category.getSelectedItem().toString();
                } catch (Exception ex) {
                }
                TimeType timeType = (TimeType) cbox_timeType.getSelectedItem();
                ProcedureResponsibilityDirection direction = (ProcedureResponsibilityDirection) cbox_direction.getSelectedItem();
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

        public void newValue(Value value);
    }
}
