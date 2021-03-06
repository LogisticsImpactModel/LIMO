package nl.fontys.sofa.limo.view.node.property.editor;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyEditorSupport;
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
import org.openide.nodes.PropertyEditorRegistration;

/**
 * This class implements property editor for procedures.
 *
 * @author Christia Neumann
 */
@PropertyEditorRegistration(targetType = Value.class)
public class ValuePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Value value = (Value) getValue();
        
        return value.getValue() + " - " + value.getMin() + " - " + value.getMax();
    }

    @Override
    public Component getCustomEditor() {
        final ValueEditor val = new ValueEditor((Value) getValue());
        val.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Only save if wanted
                if (val.shouldSafe) {
                    setValue(val.procedureValue);
                }
            }

        });
        return val;
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
    
    /**
     * Custom location editor.
     */
    private static class ValueEditor extends JDialog implements ActionListener {

        private Value procedureValue;
        private boolean shouldSafe;

        private JButton btnOk;
        private JButton btnCancel;
        
        private JComboBox<String> comboboxValueTypes;

        private JTextField textfieldValue, textfieldMin, textfieldMax;

        private JPanel singlePanel, rangePanel;
        private JLabel labelType, labelValue, labelMin, lableMax, labelError;
        
        private int activeType = 0;
        private final CellConstraints cc;

        /**
         * Create location editor and init UI.
         *
         * @param hubLocation Old location.
         */
        public ValueEditor(Value procedureValue) {
            this.procedureValue = procedureValue;
            cc = new CellConstraints();
            this.shouldSafe = false;
            setModal(true);
            setPreferredSize(new Dimension(480, 240));
            setTitle("Edit of Value");

            initComponents();
            setLocationRelativeTo(null);
        }
        /**
         * Get updated location.
         *
         * @return Location.
         */
        public Value getProcedureValue() {
            return procedureValue;
        }

        /**
         * Should the new location be saved or not.
         *
         * @return Save true/false.
         */
        public boolean isShouldSafe() {
            return shouldSafe;
        }

        /**
         * Initialize UI.
         */
        public final void initComponents() {
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
            btnCancel = new JButton(LIMOResourceBundle.getString("CANCEL"));
            btnOk = new JButton(LIMOResourceBundle.getString("SAVE"));
            comboboxValueTypes = new JComboBox<>(new String[]{LIMOResourceBundle.getString("SINGLE"), LIMOResourceBundle.getString("RANGE")});
            
            FormLayout mainLayout = new FormLayout("5px, pref, 5px, pref, 5px, pref:grow, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px");
            FormLayout rangeLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px, pref, 5px");
            FormLayout singleLayout = new FormLayout("5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px");
            this.setLayout(mainLayout);
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
            addComponentsToDialog(procedureValue);
            //ADD COMPONENTS TO LISTENER
            btnCancel.addActionListener(this);
            btnOk.addActionListener(this);
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
           this.add(btnOk, cc.xy(2, 6));
           this.add(btnCancel, cc.xy(4, 6));
           this.add(labelError, cc.xyw(2, 8, 5));
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(comboboxValueTypes)) {
                actionCombobox();
            }
            if (e.getSource().equals(btnCancel)) {
                this.dispose();
            }
            if (e.getSource().equals(btnOk)) {
                System.out.println("Save");
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
                        System.out.println(Arrays.toString(ex.getStackTrace()));
                    }
                    this.remove(singlePanel);
                    this.add(rangePanel, cc.xyw(2, 4, 5));
                    textfieldMin.setText(activeValue + "");
                } else {
                    try {
                        activeValue = Double.parseDouble(textfieldMin.getText());
                    } catch (NumberFormatException ex) {
                        System.out.println(Arrays.toString(ex.getStackTrace()));
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
            shouldSafe = true;
            if (activeType == 0) {
                try {
                    SingleValue changedValue = new SingleValue(Double.parseDouble(textfieldValue.getText()));
                    procedureValue = changedValue;
                } catch (NumberFormatException ex) {
                    labelError.setText(LIMOResourceBundle.getString("NOT_A_NUMBER"));
                }
            } else {
                try {
                    double min = Double.parseDouble(textfieldMin.getText());
                    double max = Double.parseDouble(textfieldMax.getText());
                    if (max > min) {
                        RangeValue changedValue = new RangeValue(min, max);
                        procedureValue = changedValue;
                    } else {
                        labelError.setText(LIMOResourceBundle.getString("MAX_BIGGER_MIN"));
                    }
                } catch (NumberFormatException ex) {
                    labelError.setText(LIMOResourceBundle.getString("NOT_A_NUMBER"));
                }
            }
            for (WindowListener wl : getWindowListeners()) {
                wl.windowClosing(null);
            }
            // Close window
            setVisible(false);
            dispose();
        }
    }
}
