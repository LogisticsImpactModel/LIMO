package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.DefaultTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Abstract panel for selecting BaseEntities.
 *
 * @author Matthias Brück
 */
public abstract class BaseEntitySelectionPanel<T extends BaseEntity> implements ActionListener, MouseListener, TableModelListener, WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    protected List<T> selectedEntities;
    protected List<T> allEntities;
    protected final JPanel component;
    protected JTable tblEntities;
    protected DefaultTableModel tblmdlEntities;
    private final JButton btnSelectAll, btnDeselectAll;
    private final CellConstraints cc;
    private final FormLayout layout;

    /**
     * Initializes a new BaseEntitySelectionPanel.
     */
    public BaseEntitySelectionPanel() {
        component = new JPanel();
        initAllEntities();
        selectedEntities = new ArrayList<>();
        Object[][] entityModel = new Object[allEntities.size()][2];
        for (int i = 0; i < allEntities.size(); i++) {
            entityModel[i][0] = allEntities.get(i).getName();
            entityModel[i][1] = false;
        }
        //COMPONENT BASIC
        tblmdlEntities = new DefaultTableModel(entityModel, new String[]{LIMOResourceBundle.getString("NAME"), LIMOResourceBundle.getString("SELECTED")}, new boolean[]{false, true}, new Class[]{String.class, Boolean.class});
        cc = new CellConstraints();
        layout = new FormLayout("5px, pref, 5px, pref, pref:grow, 5px", "5px, pref, 5px, pref:grow, 5px");
        component.setLayout(layout);
        //BUTTONS
        btnSelectAll = new JButton(LIMOResourceBundle.getString("SELECT_ALL"));
        btnSelectAll.addActionListener(this);
        component.add(btnSelectAll, cc.xy(2, 2));
        btnDeselectAll = new JButton(LIMOResourceBundle.getString("DESELECT_ALL"));
        btnDeselectAll.addActionListener(this);
        component.add(btnDeselectAll, cc.xy(4, 2));
        //TABLE
        tblEntities = new JTable(tblmdlEntities);
        tblmdlEntities.addTableModelListener(this);
        tblEntities.addMouseListener(this);
        JScrollPane tblEntitiesPane = new JScrollPane(tblEntities);
        component.add(tblEntitiesPane, cc.xyw(2, 4, 4));
    }

    /**
     * Inits all entities.
     */
    protected abstract void initAllEntities();

    public List<T> getSelectedEntities() {
        return selectedEntities;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSelectAll)) {
            selectedEntities.clear();
            selectedEntities.addAll(allEntities);
            for (int i = 0; i < tblmdlEntities.getRowCount(); i++) {
                tblmdlEntities.setValueAt(true, i, 1);
            }
            tblmdlEntities.fireTableDataChanged();
        }
        if (e.getSource().equals(btnDeselectAll)) {
            selectedEntities.clear();
            for (int i = 0; i < tblmdlEntities.getRowCount(); i++) {
                tblmdlEntities.setValueAt(false, i, 1);
            }
            tblmdlEntities.fireTableDataChanged();
        }
    }

    @Override
    public java.awt.Component getComponent() {
        if (component == null) {
            initAllEntities();
        }
        return component;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor settings) {
    }

    @Override
    public void validate() throws WizardValidationException {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getSource().equals(tblmdlEntities)) {
            if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
                if ((boolean) (((DefaultTableModel) e.getSource()).getValueAt(tblEntities.getSelectedRow(), 1))) {
                    selectedEntities.add(allEntities.get(tblEntities.getSelectedRow()));
                } else {
                    selectedEntities.remove(allEntities.get(tblEntities.getSelectedRow()));
                }
            }
        }
    }
}
