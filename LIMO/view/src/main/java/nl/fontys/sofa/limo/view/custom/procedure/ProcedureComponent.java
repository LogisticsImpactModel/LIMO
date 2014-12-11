package nl.fontys.sofa.limo.view.custom.procedure;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTable;
import nl.fontys.sofa.limo.view.custom.table.DragNDropTableModel;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

public class ProcedureComponent extends JPanel implements ActionListener, MouseListener {

    protected DragNDropTable table;
    protected DragNDropTableModel model;
    protected JButton addButton, deleteButton;
    protected ProcedureCategoryDAO procedureCategoryDao;
    protected Value changedValue;
    protected JComboBox procedureCategoryCheckbox, timeTypesCheckbox;
    private final ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");

    public ProcedureComponent() {
        this(new ArrayList<Procedure>());
    }

    public ProcedureComponent(List<Procedure> procedures) {
        procedureCategoryDao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
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

    public List<Procedure> getActiveTableState() {
        List<List<Object>> values = ((DragNDropTableModel) table.getModel()).getValues();
        ArrayList<Procedure> procedures = new ArrayList<>();
        for (List<Object> value : values) {
            Procedure p = new Procedure();
            p.setName((String) value.get(0));
            p.setCategory((String) value.get(1));
            p.setTime((Value) value.get(2));
            p.setTimeType((TimeType) value.get(3));
            p.setCost((Value) value.get(4));
            procedures.add(p);
        }
        return procedures;
    }

    public void setProcedureTable(List<Procedure> procedures) {
        initProceduresTable(procedures);
        model.fireTableDataChanged();
        revalidate();
        repaint();
    }

    protected void addProcedure() {
        new AddProcedureDialog(procedureCategoryDao, table);
    }

    protected void deleteProcedure(int row) {
        ((DragNDropTableModel) table.getModel()).removeRow(row);
        revalidate();
        repaint();
    }

    protected void editProcedure() {
        if (table.getSelectedColumn() == 2 || table.getSelectedColumn() == 4) {
            changedValue = (Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            Object valueAt = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            new EditValueDialog((Value) valueAt, new AddProcedureDialog.EditValueDialogListener() {

                @Override
                public void newValue(Value value) {
                    changedValue = value;
                    table.setValueAt(value, table.getSelectedRow(), table.getSelectedColumn());
                    ProcedureComponent.this.revalidate();
                    ProcedureComponent.this.repaint();
                }
            });
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
                valueList.add(procedure);
            }
        }
        model = new DragNDropTableModel(new String[]{bundle.getString("PROCEDURE"), bundle.getString("CATEGORY"), bundle.getString("TIME_COST"), bundle.getString("TIME_TYPE"), bundle.getString("MONEY_COST")},
                valueList, new Class[]{String.class, String.class, Value.class, TimeType.class, Value.class});
        table.setModel(model);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
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
}
