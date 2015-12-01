package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
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
public class ProcedureComponent extends JPanel implements ActionListener, MouseListener {

    protected DragNDropTable table;
    protected DragNDropTableModel model;
    protected JButton addButton, newButton, deleteButton;
    protected ProcedureCategoryDAO procedureCategoryDao;
    protected Value changedValue;
    protected JComboBox procedureCategoryCheckbox, timeTypesCheckbox;
    protected DefaultComboBoxModel procedureComboBoxModel;
    protected JComboBox<Procedure> proceduresComboBox;

    /**
     * Creates a new ProcedureComponent with an empty table.
     */
    public ProcedureComponent() {
        this(new ArrayList<>());
    }

    /**
     * Creates a new ProcedureComponent with a given list of procedures.
     *
     * @param procedures The procedures that have to be displayed in the table.
     */
    public ProcedureComponent(List<Procedure> procedures) {
        procedureCategoryDao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);

        proceduresComboBox = new JComboBox();
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(new JLabel(LIMOResourceBundle.getString("PROCEDURE")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        add(proceduresComboBox, c);
        DragNDropTableModel tableModel;
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DragNDropTableModel(
                new String[]{}, new ArrayList<>(), new Class[]{});
        table = new DragNDropTable(tableModel);
        initProceduresTable(procedures);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        addButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.VALID)));
        newButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        deleteButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(addButton);
        panelLeft.add(newButton);
        panelLeft.add(deleteButton);
        panel.add(panelLeft, BorderLayout.EAST);
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        add(panel, c);
        addButton.addActionListener(this);
        newButton.addActionListener(this);
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
        } else if (e.getSource().equals(newButton)) {
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
        values.stream().map((value) -> {
            Procedure p = new Procedure();
            p.setName((String) value.get(0));
            if (value.get(1) instanceof ProcedureCategory) {
                p.setCategory(((ProcedureCategory) value.get(1)).getName());
            } else { //If a procedure category is displayed in the Procedure wizard, it is represented by a String instead of a ProcedureCategory object
                p.setCategory((String) value.get(1));
            }
            p.setTime((Value) value.get(2));
            p.setTimeType((TimeType) value.get(3));
            p.setCost((Value) value.get(4));
            p.setCotwo((Value) value.get(5));
            return p;
        }).forEach((p) -> {
            procedures.add(p);
        });
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
        AddProcedureDialog addProcedureDialog = new AddProcedureDialog(procedureCategoryDao, table, deleteButton);
        addProcedureDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addProcedureDialog.setVisible(true);
        checkButtonsState();
    }

    /**
     * Deletes the specified row. Does nothing if the row is out of scope.
     *
     * @param row The row that has to be deleted.
     */
    protected void deleteProcedure(int row) {
        if (table.getRowCount() > 1) {
            ((DragNDropTableModel) table.getModel()).removeRow(row);
            revalidate();
            repaint();
            deleteButton.setEnabled(table.getRowCount() > 1);
        }
        checkButtonsState();
    }

    /**
     * Handles the editing of the specified row.
     */
    protected void editProcedure() {
        if (table.getSelectedColumn() == 2 || table.getSelectedColumn() == 4 || table.getSelectedColumn() == 5) {
            changedValue = (Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            Object valueAt = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            EditValueDialog editValueDialog = new EditValueDialog((Value) valueAt, (Value value) -> {
                changedValue = value;
                table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
                ProcedureComponent.this.revalidate();
                ProcedureComponent.this.repaint();
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
            procedures.stream().map((p) -> {
                ArrayList<Object> procedure = new ArrayList<>();
                procedure.add(p.getName());
                procedure.add(p.getCategory());
                procedure.add(p.getTime());
                procedure.add(p.getTimeType());
                procedure.add(p.getCost());
                procedure.add(p.getCotwo());
                return procedure;
            }).forEach((procedure) -> {
                valueList.add(procedure);
            });
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
            procedureCategoryCheckbox = new JComboBox(procedureCategoryDao.findAll().toArray());
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCategoryCheckbox));
        } catch (Exception e) {
            procedureCategoryCheckbox = new JComboBox(new ProcedureCategory[]{});
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCategoryCheckbox));
        }
        timeTypesCheckbox = new JComboBox(TimeType.values());
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(timeTypesCheckbox));
    }
    
    protected void checkButtonsState() {
        addButton.setEnabled(proceduresComboBox.getModel().getSize() > 0);
    }
}
