package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

public class EditValueDialog extends JDialog implements ActionListener {

    private final JButton btn_dialogSave, btn_dialogCancel;
    private final JComboBox<String> cbox_valueType;
    private final JTextField tf_value, tf_min, tf_max;
    private final JPanel singlePanel, rangePanel;
    private final JLabel lbl_type, lbl_value, lbl_min, lbl_max, lbl_error;
    private int activeType = 0;
    private final CellConstraints cc;
    private final AddProcedureDialog.EditValueDialogListener editValueDialogListener;

    public EditValueDialog(Value value, AddProcedureDialog.EditValueDialogListener editValueDialogListener) {
        //LAYOUT
        this.editValueDialogListener = editValueDialogListener;
        cc = new CellConstraints();
        FormLayout mainLayout = new FormLayout("5px, pref, 5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        FormLayout rangeLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px, pref, 5px");
        FormLayout singleLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px");
        this.setLayout(mainLayout);
        //COMPONENTS
        tf_value = new JTextField();
        tf_min = new JTextField();
        tf_max = new JTextField();
        singlePanel = new JPanel();
        rangePanel = new JPanel();
        lbl_type = new JLabel("Type: ");
        lbl_value = new JLabel("Value: ");
        lbl_min = new JLabel("Min: ");
        lbl_max = new JLabel("Max: ");
        lbl_error = new JLabel();
        lbl_error.setForeground(Color.RED);
        btn_dialogCancel = new JButton("Cancel");
        btn_dialogSave = new JButton("Save");
        cbox_valueType = new JComboBox<>(new String[]{"Single", "Range"});
        //ADD COMPONENTS TO SINGLE PANEL
        singlePanel.setLayout(singleLayout);
        singlePanel.add(lbl_value, cc.xy(2, 2));
        singlePanel.add(tf_value, cc.xy(4, 2));
        //ADD COMPONENTS TO RANGE PANEL
        rangePanel.setLayout(rangeLayout);
        rangePanel.add(lbl_min, cc.xy(2, 2));
        rangePanel.add(tf_min, cc.xy(4, 2));
        rangePanel.add(lbl_max, cc.xy(2, 4));
        rangePanel.add(tf_max, cc.xy(4, 4));
        //ADD COMPONENTS TO DIALOG
        this.add(lbl_type, cc.xy(2, 2));
        this.add(cbox_valueType, cc.xyw(4, 2, 3));
        if (value != null) {
            if (value instanceof SingleValue) {
                this.add(singlePanel, cc.xyw(2, 4, 5));
                cbox_valueType.setSelectedIndex(0);
                tf_value.setText(value.getValue() + "");
                activeType = 0;
            } else {
                this.add(rangePanel, cc.xyw(2, 4, 5));
                cbox_valueType.setSelectedIndex(1);
                tf_min.setText(value.getMin() + "");
                tf_max.setText(value.getMax() + "");
                activeType = 1;
            }
        } else {
            this.add(singlePanel, cc.xyw(2, 4, 5));
            cbox_valueType.setSelectedIndex(0);
            activeType = 0;
        }
        this.add(btn_dialogSave, cc.xy(2, 6));
        this.add(btn_dialogCancel, cc.xy(4, 6));
        this.add(lbl_error, cc.xyw(2, 8, 5));
        //ADD COMPONENTS TO LISTENER
        btn_dialogCancel.addActionListener(this);
        btn_dialogSave.addActionListener(this);
        cbox_valueType.addActionListener(this);
        //DIALOG OPTIONS
        this.setModal(true);
        this.setSize(300, 350);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cbox_valueType)) {
            if (activeType != cbox_valueType.getSelectedIndex()) {
                double activeValue;
                if (activeType == 0) {
                    activeValue = 0;
                    try {
                        activeValue = Double.parseDouble(tf_value.getText());
                    } catch (NumberFormatException ex) {
                    }
                    this.remove(singlePanel);
                    this.add(rangePanel, cc.xyw(2, 4, 5));
                    tf_min.setText(activeValue + "");
                    lbl_error.setText("");
                } else {
                    activeValue = 0;
                    try {
                        activeValue = Double.parseDouble(tf_min.getText());
                    } catch (NumberFormatException ex) {
                    }
                    this.remove(rangePanel);
                    this.add(singlePanel, cc.xyw(2, 4, 5));
                    tf_value.setText(activeValue + "");
                    lbl_error.setText("");
                }
                activeType = cbox_valueType.getSelectedIndex();
                this.revalidate();
                this.repaint();
            }
        }
        if (e.getSource().equals(btn_dialogCancel)) {
            this.dispose();
        }
        if (e.getSource().equals(btn_dialogSave)) {
            if (activeType == 0) {
                try {
                    SingleValue changedValue = new SingleValue(Double.parseDouble(tf_value.getText()));
                    editValueDialogListener.newValue(changedValue);
                    this.dispose();
                } catch (NumberFormatException ex) {
                    lbl_error.setText("NOT A NUMBER");
                }
            } else {
                try {
                    double min = Double.parseDouble(tf_min.getText());
                    double max = Double.parseDouble(tf_max.getText());
                    if (max > min) {
                        RangeValue changedValue = new RangeValue(min, max);
                        editValueDialogListener.newValue(changedValue);
                        this.dispose();
                    } else {
                        lbl_error.setText("MAX MUST BE BIGGER THAN MIN");
                    }
                } catch (NumberFormatException ex) {
                    lbl_error.setText("NOT A NUMBER");
                }
            }
        }
    }
}
