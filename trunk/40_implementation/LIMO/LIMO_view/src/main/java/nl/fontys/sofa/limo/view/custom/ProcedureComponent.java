package nl.fontys.sofa.limo.view.custom;

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
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureResponsibilityDirection;
import nl.fontys.sofa.limo.domain.component.procedure.TimeType;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import nl.fontys.sofa.limo.view.custom.procedure.AddProcedureDialog;
import nl.fontys.sofa.limo.view.custom.procedure.EditValueDialog;
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
    protected JComboBox procedureCategoryCheckbox, timeTypesCheckbox, directionCheckbox;

    public ProcedureComponent() {
        this(new ArrayList<Procedure>());
    }

    public ProcedureComponent(List<Procedure> procedures) {
        procedureCategoryDao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
        CellConstraints cc = new CellConstraints();
        this.setLayout(new FormLayout("5px, pref:grow, 5px, pref, 5px", "5px, pref, 10px, pref, pref:grow, 5px"));
        DragNDropTableModel tableModel = new DragNDropTableModel(new String[]{}, new ArrayList<List<Object>>(), new Class[]{});
        this.table = new DragNDropTable(tableModel);
        this.initProceduresTable(procedures);
        JScrollPane scrollPane = new JScrollPane(table);
        this.addButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        this.deleteButton = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        this.add(scrollPane, cc.xywh(2, 2, 1, 4));
        this.add(addButton, cc.xy(4, 2));
        this.add(deleteButton, cc.xy(4, 4));
        this.addButton.addActionListener(this);
        this.deleteButton.addActionListener(this);
        this.table.addMouseListener(this);
        this.setVisible(true);
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
            p.setTimeType((TimeType) value.get(2));
            p.setTime((Value) value.get(3));
            p.setCost((Value) value.get(4));
            p.setDirection((ProcedureResponsibilityDirection) value.get(5));
            procedures.add(p);
        }
        return procedures;
    }

    public void setProcedureTable(List<Procedure> procedures) {
        initProceduresTable(procedures);
        model.fireTableDataChanged();
        this.revalidate();
        this.repaint();
    }

    protected void addProcedure() {
        new AddProcedureDialog(procedureCategoryDao, table);
    }

    protected void deleteProcedure(int row) {
        ((DragNDropTableModel) table.getModel()).removeRow(row);
        this.revalidate();
        this.repaint();
    }

    protected void editProcedure() {
        if (table.getSelectedColumn() == 3 || table.getSelectedColumn() == 4) {
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
                procedure.add(p.getTimeType());
                procedure.add(p.getTime());
                procedure.add(p.getCost());
                procedure.add(p.getDirection());
                valueList.add(procedure);
            }
        }
        model = new DragNDropTableModel(new String[]{"Name", "Category", "Time Type", "Time Cost", "Money Cost", "Direction"},
                valueList, new Class[]{String.class, String.class, TimeType.class, Value.class, Value.class, ProcedureResponsibilityDirection.class});
        table.setModel(model);
        try {
            procedureCategoryCheckbox = new JComboBox(procedureCategoryDao.findAll().toArray());
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCategoryCheckbox));
        } catch (Exception e) {
            procedureCategoryCheckbox = new JComboBox(new ProcedureCategory[]{});
            table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(procedureCategoryCheckbox));
        }
        timeTypesCheckbox = new JComboBox(TimeType.values());
        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(timeTypesCheckbox));
        directionCheckbox = new JComboBox(ProcedureResponsibilityDirection.values());
        table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(directionCheckbox));
    }
}
