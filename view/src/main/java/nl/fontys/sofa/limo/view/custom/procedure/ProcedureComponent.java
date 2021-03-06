package nl.fontys.sofa.limo.view.custom.procedure;

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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.ProcedureTableModel;
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

    protected JTable table;
    protected ProcedureTableModel model;
    protected JButton addButton, newButton, deleteButton;
    protected ProcedureCategoryDAO procedureCategoryDao;
    protected Value changedValue;
    protected JComboBox procedureCategoryCheckbox, timeTypesCheckbox;
    protected DefaultComboBoxModel procedureComboBoxModel;
    protected JComboBox<Procedure> proceduresComboBox;
    protected ProcedureService service;
    protected List<Procedure> allProcedures;
    protected List<Procedure> tableProcedures;

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
        tableProcedures = procedures;
        initProcedureService();
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
        ProcedureTableModel tableModel;
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new ProcedureTableModel(procedures);
        table = new JTable(tableModel);
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
        setProcedureComboBox();
        checkButtonsState();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(deleteButton)) {
            int rowToDelete = table.getSelectedRow();
            if (rowToDelete > -1) {
                deleteProcedure(rowToDelete);
            }
        } else if (e.getSource().equals(addButton)) {
            addClicked();
            setProcedureComboBox();
            checkButtonsState();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(table)) {
            if (e.getClickCount() > 1) {
                editProcedure();
            }
        }
        checkButtonsState();
    }

    /**
     * Returns a list with all procedures present in the table.
     *
     * @return A list with all procedures that are in the table at the moment.
     */
    public List<Procedure> getActiveTableState() {
        return model.getProcedures();
    }

    /**
     * Sets the table to a new procedure list.
     *
     * @param procedures The new list of procedures that has to be used.
     */
    public void setProcedureTable(List<Procedure> procedures) {
        tableProcedures = procedures;
        initProceduresTable(procedures);
        model.fireTableDataChanged();
        revalidate();
        repaint();
    }

    /**
     * Handles the adding of a procedure via a dialog.
     */
    protected void addProcedure() {
        AddProcedureDialog addProcedureDialog = new AddProcedureDialog(procedureCategoryDao, table);
        addProcedureDialog.setListener((Procedure procedure) -> {
            deleteButton.setEnabled(true);
            tableProcedures.add(procedure);
        });
        addProcedureDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addProcedureDialog.setVisible(true);
        initProcedureService();
        setProcedureComboBox();
        checkButtonsState();
    }

    /**
     * Deletes the specified row. Does nothing if the row is out of scope.
     *
     * @param row The row that has to be deleted.
     */
    protected void deleteProcedure(int row) {
        model.removeProcedue(row);
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
        tableProcedures = procedures;
        model = new ProcedureTableModel(procedures);
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
        deleteButton.setEnabled(tableProcedures.size() > 1 && table.getSelectedRow() != -1);
    }

    private void initProcedureService() {
        service = Lookup.getDefault().lookup(ProcedureService.class);
        allProcedures = service.findAll();
    }

    protected void setProcedureComboBox() {
        ArrayList<String> allProcedureNames = new ArrayList<>();
        List<String> usedProcedures = new ArrayList<>();
        for (int row = 0; row < table.getRowCount(); row++) {
            usedProcedures.add((String) table.getValueAt(row, 0));
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

    protected void addClicked() {
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
        }
    }
}
