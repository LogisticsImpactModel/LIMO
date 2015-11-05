package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import nl.fontys.sofa.limo.api.dao.ProcedureDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTable;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * This Panel handles everything about a procedure component. It shows you a
 * given list of procedures, you can edit them, add new ones and delete some
 * from the list.
 *
 * @author Matthias Brück
 */
public class StandartProcedureComponent extends JPanel implements ActionListener, MouseListener {

    protected DragNDropTable table;
    protected DragNDropTableModel model;
    protected JButton addButton, deleteButton;
    protected ProcedureDAO procedureDao;
    protected Value changedValue;
    protected JComboBox procedureCheckbox, timeTypesCheckbox;

    /**
     * Creates a new ProcedureComponent with an empty table.
     */
    public StandartProcedureComponent() {
        this(new ArrayList<Procedure>());
    }

    /**
     * Creates a new ProcedureComponent with a given list of procedures.
     *
     * @param procedures The procedures that have to be displayed in the table.
     */
    public StandartProcedureComponent(List<Procedure> procedures) {
        procedureDao = Lookup.getDefault().lookup(ProcedureDAO.class);
        CellConstraints cc = new CellConstraints();
        setLayout(new FormLayout("5px, pref:grow, 5px, pref, 5px", "5px, pref, 10px, pref, pref:grow, 5px"));
        DragNDropTableModel tableModel = new DragNDropTableModel(
                new String[]{}, new ArrayList<List<Object>>(), new Class[]{});
        table = new DragNDropTable(tableModel);
        initProceduresTable(procedures);
        JScrollPane scrollPane = new JScrollPane(table);
        addButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        deleteButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        add(scrollPane, cc.xywh(2, 2, 1, 4));
        add(addButton, cc.xy(4, 2));
        add(deleteButton, cc.xy(4, 4));
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        table.addMouseListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(deleteButton)) {
            int rowToDelete = table.getSelectedRow();
            if (rowToDelete > -1) {
                deleteProcedure(rowToDelete);
            }
        } else if (e.getSource().equals(addButton)) {
            addProcedure();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(table)) {
            if (e.getClickCount() > 1) {
                editProcedure();
            }
        }
    }

    /**
     * Returns a list with all procedures present in the table.
     *
     * @return A list with all procedures that are in the table at the moment.
     */
    public List<Procedure> getActiveTableState() {
        List<List<Object>> values = ((DragNDropTableModel) table.getModel()).getValues();
        ArrayList<Procedure> procedures = new ArrayList<>();
        for (List<Object> value : values) {
            Procedure p = new Procedure();
            p.setName((String) value.get(0));
            if (value.get(1) instanceof Procedure) {
                p.setCategory(((Procedure) value.get(1)).getName());
            } else { //If a procedure category is displayed in the Procedure wizard, it is represented by a String instead of a ProcedureCategory object
                p.setCategory((String) value.get(1));
            }
            p.setTime((Value) value.get(2));
            p.setTimeType((TimeType) value.get(3));
            p.setCost((Value) value.get(4));
            p.setCotwo((Value) value.get(5));
            procedures.add(p);
        }
        return procedures;
    }

    /**
     * Sets the table to a new procedure list.
     *
     * @param procedures The new list of procedures that has to be used.
     */
    public void setProcedureTable(List<Procedure> procedures) {
        initProceduresTable(procedures);
        model.fireTableDataChanged();
        revalidate();
        repaint();
    }

    /**
     * Handles the adding of a procedure via a dialog.
     */
    protected void addProcedure() {
        AddStandartProcedureDialog addProcedureDialog = new AddStandartProcedureDialog(procedureDao, table);
        addProcedureDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addProcedureDialog.setVisible(true);
    }

    /**
     * Deletes the specified row. Does nothing if the row is out of scope.
     *
     * @param row The row that has to be deleted.
     */
    protected void deleteProcedure(int row) {
        ((DragNDropTableModel) table.getModel()).removeRow(row);
        revalidate();
        repaint();
    }

    /**
     * Handles the editing of the specified row.
     */
    protected void editProcedure() {
        if (table.getSelectedColumn() == 2 || table.getSelectedColumn() == 4 || table.getSelectedColumn() == 5) {
            changedValue = (Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            Object valueAt = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            EditValueDialog editValueDialog = new EditValueDialog((Value) valueAt, new AddProcedureDialog.EditValueDialogListener() {

                @Override
                public void newValue(Value value) {
                    changedValue = value;
                    table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
                    StandartProcedureComponent.this.revalidate();
                    StandartProcedureComponent.this.repaint();
                }
            });
            editValueDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editValueDialog.setVisible(true);
        }
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

    /**
     * Initializes the procedure table with the given list of procedures.
     *
     * @param procedures The list of procedures that has to be used in the
     * table.
     */
    private void initProceduresTable(List<Procedure> procedures) {
        List<List<Object>> valueList = new ArrayList<>();
        if (procedures != null) {
            for (Procedure p : procedures) {
                ArrayList<Object> procedure = new ArrayList<>();
                procedure.add(p.getName());
                procedure.add(p.getCategory());
                procedure.add(p.getTime());
                procedure.add(p.getTimeType());
                procedure.add(p.getCost());
                procedure.add(p.getCotwo());
                valueList.add(procedure);
            }
        }
        model = new DragNDropTableModel(new String[]{LIMOResourceBundle.getString("PROCEDURE"), LIMOResourceBundle.getString("CATEGORY"), LIMOResourceBundle.getString("TIME_COST"), LIMOResourceBundle.getString("TIME_TYPE"), LIMOResourceBundle.getString("MONEY_COST"), LIMOResourceBundle.getString("CO2")},
                valueList, new Class[]{String.class, String.class, Value.class, TimeType.class, Value.class, Value.class});
        table.setModel(model);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        DefaultTableCellRenderer middleRenderer = new DefaultTableCellRenderer();
        middleRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(middleRenderer);

        try {
            procedureCheckbox = new JComboBox(procedureDao.findAll().toArray());
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCheckbox));
        } catch (Exception e) {
            procedureCheckbox = new JComboBox(new Procedure[]{});
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCheckbox));
        }
        timeTypesCheckbox = new JComboBox(TimeType.values());
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(timeTypesCheckbox));
    }
}
