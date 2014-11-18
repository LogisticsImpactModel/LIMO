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
        btn_add = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btn_delete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
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
        new AddProcedureDialog(procedureCategoryDao, table);
    }

    private void deleteProcedure(int row) {
        ((DragNDropTableModel) table.getModel()).removeRow(row);
        this.revalidate();
        this.repaint();
    }

    private void editProcedure() {
        if (table.getSelectedColumn() == 3 || table.getSelectedColumn() == 4) {
            changedValue = (Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
            new EditValueDialog((Value) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()), new AddProcedureDialog.EditValueDialogListener() {

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

}
