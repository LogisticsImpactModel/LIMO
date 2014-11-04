package nl.fontys.sofa.limo.view.custom;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTable;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.openide.util.Lookup;

/**
 * @author Matthias Brück
 */
public class ProcedureComponent extends JPanel implements ActionListener, MouseListener {

    private DragNDropTable table;
    private DragNDropTableModel model;
    private JButton btn_add, btn_delete;
    private JScrollPane sc_pane;
    private ProcedureCategoryDAO procedureCategoryDao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
    private Value changedValue;
    CellConstraints cc;

    public ProcedureComponent() {
        this(null);
    }

    public ProcedureComponent(List<Procedure> procedures) {
        //LAYOUT
        cc = new CellConstraints();
        FormLayout layout = new FormLayout("5px, pref:grow, 5px, pref, 5px", "5px, pref, 10px, pref, pref:grow, 5px");
        this.setLayout(layout);
        //TABLEMODEL
        List<List<Object>> valueList = new ArrayList<>();
        if (procedures != null) {
            for (Procedure p : procedures) {
                ArrayList<Object> values = new ArrayList<>();
                values.add(p.getName());
                values.add(p.getCategory());
                values.add(p.getTimeType());
                values.add(p.getTime());
                values.add(p.getCost());
                values.add(p.getDirection());
                valueList.add(values);
            }
        }
        model = new DragNDropTableModel(new String[]{"Name", "Category", "Time Type", "Time Cost", "Money Cost", "Direction"},
                valueList, new Class[]{String.class, String.class, TimeType.class, Value.class, Value.class, ProcedureResponsibilityDirection.class});
        //TABLE
        table = new DragNDropTable(model);
        try {
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(procedureCategoryDao.findAll().toArray())));
        } catch (Exception e) {
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(new ProcedureCategory[]{})));
        }
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox(TimeType.values())));
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JComboBox(ProcedureResponsibilityDirection.values())));
        //OTHER COMPONENTS
        sc_pane = new JScrollPane(table);
        btn_add = new JButton("+");
        btn_delete = new JButton("-");
        //ADD COMPONENTS TO PANEL
        this.add(sc_pane, cc.xywh(2, 2, 1, 4));
        this.add(btn_add, cc.xy(4, 2));
        this.add(btn_delete, cc.xy(4, 4));
        //ADD COMPONENTS TO LISTENER
        btn_add.addActionListener(this);
        btn_delete.addActionListener(this);
        table.addMouseListener(this);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn_delete)) {
            int rowToDelete = table.getSelectedRow();
            if (rowToDelete > -1) {
                deleteProcedure(rowToDelete);
            }
        } else if (e.getSource().equals(btn_add)) {
            addProcedure();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(table)) {
            System.out.println(getActiveTableState());
            if (e.getClickCount() > 1) {
                editProcedure();
            }
        }
    }

    public List<Procedure> getActiveTableState() {
        List<List<Object>> values = ((DragNDropTableModel) table.getModel()).getValues();
        ArrayList<Procedure> procedures = new ArrayList<>();
        for (List<Object> value : values) {
            Procedure p = new Procedure();
            p.setName((String) value.get(0));
            p.setCategory((String) value.get(1));
            p.setTimeType((TimeType) value.get(2));
            p.setTime((Value) value.get(3));
            p.setCost((Value) value.get(4));
            p.setDirection((ProcedureResponsibilityDirection) value.get(5));
            procedures.add(p);
        }
        return procedures;
    }

    public void setProcedureTable(List<Procedure> procedures) {
        List<List<Object>> valueList = new ArrayList<>();
        if (procedures != null) {
            for (Procedure p : procedures) {
                ArrayList<Object> values = new ArrayList<>();
                values.add(p.getName());
                values.add(p.getCategory());
                values.add(p.getTimeType());
                values.add(p.getTime());
                values.add(p.getCost());
                values.add(p.getDirection());
                valueList.add(values);
            }
        }
        model = new DragNDropTableModel(new String[]{"Name", "Category", "Time Type", "Time Cost", "Money Cost", "Direction"},
                valueList, new Class[]{String.class, String.class, TimeType.class, Value.class, Value.class, ProcedureResponsibilityDirection.class});
        table.setModel(model);
        model.fireTableDataChanged();
        this.revalidate();
        this.repaint();
    }

    private void addProcedure() {
        new AddProcedureDialog();
    }

    private void deleteProcedure(int row) {
        ((DragNDropTableModel) table.getModel()).removeRow(row);
        this.revalidate();
        this.repaint();
    }

    private void editProcedure() {
        changedValue = (Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
        new EditValueDialog((Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
        table.setValueAt(changedValue, table.getSelectedRow(), table.getSelectedColumn());
        this.revalidate();
        this.repaint();

    }

    // <editor-fold desc="UNUSED LISTENER METHODS" defaultstate="collapsed">
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    //</editor-fold>

    private class AddProcedureDialog extends JDialog implements ActionListener {

        private JButton btn_addSave, btn_addCancel, btn_addTime, btn_addCost;
        private final JLabel lbl_name, lbl_category, lbl_timeType, lbl_time, lbl_cost, lbl_direction;
        private final JTextField tf_name, tf_cost, tf_time;
        private JComboBox cbox_timeType, cbox_category, cbox_direction;
        private Value timeValue, costValue;
        private Procedure newProcedure;

        public AddProcedureDialog() {
            //LAYOUT
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
            tf_time = new JTextField();
            tf_time.setEditable(false);
            btn_addTime = new JButton("...");
            lbl_cost = new JLabel("Money Cost:");
            tf_cost = new JTextField();
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
                new EditValueDialog(costValue);
                if (costValue != null) {
                    costValue = changedValue;
                    tf_cost.setText(costValue.toString());
                    this.revalidate();
                    this.repaint();
                }
            }
            if (e.getSource().equals(btn_addTime)) {
                new EditValueDialog(timeValue);
                timeValue = changedValue;
                if (timeValue != null) {
                    tf_time.setText(timeValue.toString());
                    this.revalidate();
                    this.repaint();
                }
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
    }

    private class EditValueDialog extends JDialog implements ActionListener {

        private final JButton btn_dialogSave, btn_dialogCancel;
        private final JComboBox<String> cbox_valueType;
        private final JTextField tf_value, tf_min, tf_max;
        private final JPanel singlePanel, rangePanel;
        private final JLabel lbl_type, lbl_value, lbl_min, lbl_max, lbl_error;
        private int activeType = 0;

        public EditValueDialog(Value value) {
            //LAYOUT
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
            this.setSize(200, 250);
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
                        try {
                            if (tf_value.getText().equals("")) {
                                activeValue = 0;
                            } else {
                                activeValue = Double.parseDouble(tf_value.getText());
                            }
                            this.remove(singlePanel);
                            this.add(rangePanel, cc.xyw(2, 4, 5));
                            tf_min.setText(activeValue + "");
                            lbl_error.setText("");
                        } catch (NumberFormatException ex) {
                            cbox_valueType.setSelectedIndex(0);
                        }
                    } else {
                        try {
                            if (tf_value.getText().equals("")) {
                                activeValue = 0;
                            } else {
                                activeValue = Double.parseDouble(tf_min.getText());
                            }
                            this.remove(rangePanel);
                            this.add(singlePanel, cc.xyw(2, 4, 5));
                            tf_value.setText(activeValue + "");
                            lbl_error.setText("");
                        } catch (NumberFormatException ex) {
                            cbox_valueType.setSelectedIndex(1);
                        }
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
                        changedValue = new SingleValue(Double.parseDouble(tf_value.getText()));
                        this.dispose();
                    } catch (NumberFormatException ex) {
                        lbl_error.setText("NOT A NUMBER");
                    }
                } else {
                    try {
                        double min = Double.parseDouble(tf_min.getText());
                        double max = Double.parseDouble(tf_max.getText());
                        if (max > min) {
                            changedValue = new RangeValue(min, max);
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
}
