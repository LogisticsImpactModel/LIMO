package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This class extends the JDialog and offers editing of the "Value" type from
 * the domain model.
 *
 * @author Matthias Brück
 */
public class EditStandartValueDialog extends JDialog implements ActionListener {

    private JButton buttonSave, buttonCancel;
    private JComboBox<String> comboboxValueTypes;
    private JTextField textfieldValue, textfieldMin, textfieldMax;
    private JPanel singlePanel, rangePanel;
    private JLabel labelType, labelValue, labelMin, lableMax, labelError;
    private int activeType = 0;
    private final CellConstraints cc;
    private final AddStandartProcedureDialog.EditStandartValueDialogListener editValueDialogListener;

    public EditStandartValueDialog(Value value, AddStandartProcedureDialog.EditStandartValueDialogListener editValueDialogListener) {
        //LAYOUT
        this.editValueDialogListener = editValueDialogListener;
        cc = new CellConstraints();
        FormLayout mainLayout = new FormLayout("5px, pref, 5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
        FormLayout rangeLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px, pref, 5px");
        FormLayout singleLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px");
        this.setLayout(mainLayout);
        //COMPONENTS
        initComponents();
        //ADD COMPONENTS TO SINGLE PANEL
        singlePanel.setLayout(singleLayout);
        singlePanel.add(labelValue, cc.xy(2, 2));
        singlePanel.add(textfieldValue, cc.xy(4, 2));
        //ADD COMPONENTS TO RANGE PANEL
        rangePanel.setLayout(rangeLayout);
        rangePanel.add(labelMin, cc.xy(2, 2));
        rangePanel.add(textfieldMin, cc.xy(4, 2));
        rangePanel.add(lableMax, cc.xy(2, 4));
        rangePanel.add(textfieldMax, cc.xy(4, 4));
        //ADD COMPONENTS TO DIALOG
        addComponentsToDialog(value);
        //ADD COMPONENTS TO LISTENER
        buttonCancel.addActionListener(this);
        buttonSave.addActionListener(this);
        comboboxValueTypes.addActionListener(this);
        //DIALOG OPTIONS
        this.setModal(true);
        this.setSize(300, 350);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    /**
     * Initializes the components.
     */
    private void initComponents() {
        textfieldValue = new JTextField();
        textfieldMin = new JTextField();
        textfieldMax = new JTextField();
        singlePanel = new JPanel();
        rangePanel = new JPanel();
        labelType = new JLabel(LIMOResourceBundle.getString("TYPE"));
        labelValue = new JLabel(LIMOResourceBundle.getString("VALUE"));
        labelMin = new JLabel(LIMOResourceBundle.getString("MIN"));
        lableMax = new JLabel(LIMOResourceBundle.getString("MAX"));
        labelError = new JLabel();
        labelError.setForeground(Color.RED);
        buttonCancel = new JButton(LIMOResourceBundle.getString("CANCEL"));
        buttonSave = new JButton(LIMOResourceBundle.getString("SAVE"));
        comboboxValueTypes = new JComboBox<>(new String[]{LIMOResourceBundle.getString("SINGLE"), LIMOResourceBundle.getString("RANGE")});
    }

    /**
     * Adds the components to the dialog.
     *
     * @param value The value that has to be used.
     */
    private void addComponentsToDialog(Value value) {
        this.add(labelType, cc.xy(2, 2));
        this.add(comboboxValueTypes, cc.xyw(4, 2, 3));
        if (value != null) {
            if (value instanceof SingleValue) {
                this.add(singlePanel, cc.xyw(2, 4, 5));
                comboboxValueTypes.setSelectedIndex(0);
                textfieldValue.setText(value.getValue() + "");
                activeType = 0;
            } else {
                this.add(rangePanel, cc.xyw(2, 4, 5));
                comboboxValueTypes.setSelectedIndex(1);
                textfieldMin.setText(value.getMin() + "");
                textfieldMax.setText(value.getMax() + "");
                activeType = 1;
            }
        } else {
            this.add(singlePanel, cc.xyw(2, 4, 5));
            comboboxValueTypes.setSelectedIndex(0);
            activeType = 0;
        }
        this.add(buttonSave, cc.xy(2, 6));
        this.add(buttonCancel, cc.xy(4, 6));
        this.add(labelError, cc.xyw(2, 8, 5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(comboboxValueTypes)) {
            actionCombobox();
        }
        if (e.getSource().equals(buttonCancel)) {
            this.dispose();
        }
        if (e.getSource().equals(buttonSave)) {
            actionSave();
        }
    }

    /**
     * The action that has to happen when the ActionListener registrated an
     * action in the Combobox. It sets the field to either a Single or Range
     * Value. It uses the Min value as value when switching from range to single
     * and the value as Min when switching from single to range value.
     */
    private void actionCombobox() {
        if (activeType != comboboxValueTypes.getSelectedIndex()) {
            double activeValue = 0;
            if (activeType == 0) {
                try {
                    activeValue = Double.parseDouble(textfieldValue.getText());
                } catch (NumberFormatException ex) {
                    labelError.setText(Arrays.toString(ex.getStackTrace()));
                }
                this.remove(singlePanel);
                this.add(rangePanel, cc.xyw(2, 4, 5));
                textfieldMin.setText(activeValue + "");
            } else {
                try {
                    activeValue = Double.parseDouble(textfieldMin.getText());
                } catch (NumberFormatException ex) {
                    labelError.setText(Arrays.toString(ex.getStackTrace()));
                }
                this.remove(rangePanel);
                this.add(singlePanel, cc.xyw(2, 4, 5));
                textfieldValue.setText(activeValue + "");
            }
            labelError.setText("");
            activeType = comboboxValueTypes.getSelectedIndex();
            this.revalidate();
            this.repaint();
        }
    }

    /**
     * The action that happens when the save-button was pressed. Tries to parse
     * the textfields to Double values. If it works it gets saved in the given
     * DialogListener. Sets the error label otherwise.
     */
    private void actionSave() {
        if (activeType == 0) {
            try {
                SingleValue changedValue = new SingleValue(Double.parseDouble(textfieldValue.getText()));
                editValueDialogListener.newValue(changedValue);
                this.dispose();
            } catch (NumberFormatException ex) {
                labelError.setText(LIMOResourceBundle.getString("NOT_A_NUMBER"));
            }
        } else {
            try {
                double min = Double.parseDouble(textfieldMin.getText());
                double max = Double.parseDouble(textfieldMax.getText());
                if (max > min) {
                    RangeValue changedValue = new RangeValue(min, max);
                    editValueDialogListener.newValue(changedValue);
                    this.dispose();
                } else {
                    labelError.setText(LIMOResourceBundle.getString("MAX_BIGGER_MIN"));
                }
            } catch (NumberFormatException ex) {
                labelError.setText(LIMOResourceBundle.getString("NOT_A_NUMBER"));
            }
        }
    }
}
