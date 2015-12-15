package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * This class extends the JDialog and offers the creation of new procedures.
 *
 * @author Matthias Brück
 */
public class AddProcedureDialog extends JDialog implements ActionListener {

    public static interface SaveListener {

        void onSave(Procedure procedure);
    }

    private JButton saveButton, cancelButton, addTimeButton, addCostButton, addCotwoButton;
    private JTextField nameTextField, costTextField, timeTextField, cotwoTextField;
    private JComboBox timeTypeCombobox, categoryCombobox;
    private Value timeValue, costValue, cotwoValue;
    private Procedure newProcedure;
    private final JTable table;
    private final CellConstraints cc;
    private JCheckBox templateCheckbox;
    private ProcedureService service;
    private SaveListener listener;

    public AddProcedureDialog(ProcedureCategoryDAO procedureCategoryDao, JTable table) {
        this.table = table;
        cc = new CellConstraints();
        //LAYOUT
        FormLayout layout = new FormLayout("5px, pref, 5px, pref, pref:grow, 5px, pref, 5px",
                "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref:grow, pref:grow, 5px");
        this.setLayout(layout);
        //COMPONENTS
        initComponents(procedureCategoryDao.findAll().toArray());
        //ADD COMPONENTS
        addComponents();
        //ADD COMPONENTS TO LISTENER
        cancelButton.addActionListener(this);
        saveButton.addActionListener(this);
        addCostButton.addActionListener(this);
        addTimeButton.addActionListener(this);
        addCotwoButton.addActionListener(this);
        //DIALOG OPTIONS
        this.setSize(450, 300);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setTitle(LIMOResourceBundle.getString("PROCEDURES"));
        service = Lookup.getDefault().lookup(ProcedureService.class);
    }

    public void setListener(SaveListener listener) {
        this.listener = listener;
    }

    /**
     * Initializes the components.
     *
     * @param categories the categories that gets used in the categoryCombobox.
     */
    private void initComponents(Object[] categories) {
        nameTextField = new JTextField();
        categoryCombobox = new JComboBox(categories);
        categoryCombobox.setSelectedIndex(0);
        timeTypeCombobox = new JComboBox(TimeType.values());
        timeValue = new SingleValue(0.0);
        timeTextField = new JTextField(timeValue.toString());
        timeTextField.setEditable(false);
        addTimeButton = new JButton("...");
        costValue = new SingleValue(0.0);
        costTextField = new JTextField(costValue.toString());
        costTextField.setEditable(false);
        addCostButton = new JButton("...");
        cotwoValue = new SingleValue(0.0);
        cotwoTextField = new JTextField(costValue.toString());
        cotwoTextField.setEditable(false);
        addCotwoButton = new JButton("...");
        saveButton = new JButton(LIMOResourceBundle.getString("SAVE"));
        cancelButton = new JButton(LIMOResourceBundle.getString("CANCEL"));
        templateCheckbox = new JCheckBox("Save as template");
        // add as template by default
        templateCheckbox.setSelected(true);
    }

    /**
     * Adds the component to the dialog.
     */
    private void addComponents() {
        this.add(new JLabel(LIMOResourceBundle.getString("NAME")), cc.xy(2, 2));
        this.add(nameTextField, cc.xyw(4, 2, 2));
        this.add(new JLabel(LIMOResourceBundle.getString("CATEGORY")), cc.xy(2, 4));
        this.add(categoryCombobox, cc.xyw(4, 4, 2));
        this.add(new JLabel(LIMOResourceBundle.getString("TIME_TYPE")), cc.xy(2, 6));
        this.add(timeTypeCombobox, cc.xyw(4, 6, 2));
        this.add(new JLabel(LIMOResourceBundle.getString("TIME_COST")), cc.xy(2, 8));
        this.add(timeTextField, cc.xyw(4, 8, 2));
        this.add(addTimeButton, cc.xy(7, 8));
        this.add(new JLabel(LIMOResourceBundle.getString("MONEY_COST_PROCEDURE")), cc.xy(2, 10));
        this.add(costTextField, cc.xyw(4, 10, 2));
        this.add(addCostButton, cc.xy(7, 10));
        this.add(new JLabel(LIMOResourceBundle.getString("CO2")), cc.xy(2, 12));
        this.add(cotwoTextField, cc.xyw(4, 12, 2));
        this.add(addCotwoButton, cc.xy(7, 12));
        this.add(templateCheckbox, cc.xy(2, 14));
        this.add(saveButton, cc.xy(2, 16));
        this.add(cancelButton, cc.xy(4, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addCostButton)) {
            EditValueDialog editValueDialog = new EditValueDialog(costValue, (Value changedValue) -> {
                if (costValue != null) {
                    costValue = changedValue;
                    costTextField.setText(costValue.toString());
                    AddProcedureDialog.this.revalidate();
                    AddProcedureDialog.this.repaint();
                }
            });
            editValueDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editValueDialog.setVisible(true);
        }
        if (e.getSource().equals(addTimeButton)) {
            EditValueDialog editValueDialog = new EditValueDialog(timeValue, (Value changedValue) -> {
                if (timeValue != null) {
                    timeValue = changedValue;
                    timeTextField.setText(timeValue.toString());
                    AddProcedureDialog.this.revalidate();
                    AddProcedureDialog.this.repaint();
                }
            });
            editValueDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editValueDialog.setVisible(true);
        }
        if (e.getSource().equals(addCotwoButton)) {
            EditValueDialog editValueDialog = new EditValueDialog(cotwoValue, (Value changedValue) -> {
                if (cotwoValue != null) {
                    cotwoValue = changedValue;
                    cotwoTextField.setText(cotwoValue.toString());
                    AddProcedureDialog.this.revalidate();
                    AddProcedureDialog.this.repaint();
                }
            });
            editValueDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editValueDialog.setVisible(true);
        }
        if (e.getSource().equals(cancelButton)) {
            this.dispose();
        }
        if (e.getSource().equals(saveButton)) {
            actionSave();
        }
    }

    /**
     * This method represents the action that happens when the save button was
     * pressed. It validates the model and closes the dialog if everythin is
     * valid.
     */
    private void actionSave() {
        if (isValidProcedure()) {
            String name = nameTextField.getText();
            String category = "";
            try {
                category = categoryCombobox.getSelectedItem().toString();
            } catch (Exception ex) {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
            TimeType timeType = (TimeType) timeTypeCombobox.getSelectedItem();
            newProcedure = new Procedure(name, category, costValue, timeValue, timeType, cotwoValue);
            List<Object> newRow = new ArrayList<>();
            newRow.add(newProcedure.getName());
            newRow.add(newProcedure.getCategory());
            newRow.add(newProcedure.getTime());
            newRow.add(newProcedure.getTimeType());
            newRow.add(newProcedure.getCost());
            newRow.add(newProcedure.getCotwo());
            ((DragNDropTableModel) table.getModel()).addRow(newRow);
            ((DragNDropTableModel) table.getModel()).fireTableDataChanged();
            if (templateCheckbox.isSelected()) {
                service.insert(newProcedure);
            }
            if (listener != null) {
                listener.onSave(newProcedure);
            }
            table.revalidate();
            table.repaint();
            templateCheckbox.setSelected(true);
            this.dispose();
        }
    }

    /**
     * Checks if the model is valid. Returns true if it is and false if it is
     * not.
     *
     * @return True if the model is valid, false if not.
     */
    private boolean isValidProcedure() {
        return !nameTextField.getText().isEmpty() && !costTextField.getText().isEmpty();
    }

    /**
     * This listener offers functionality for what happens when a value of a
     * procedure got changed.
     *
     * @author Sven Mäurer
     */
    public interface EditValueDialogListener {

        /**
         * Handles what happens when a new value for a procedure got specified.
         *
         * @param value The new value for a procedure.
         */
        void newValue(Value value);
    }
}
