package nl.fontys.sofa.limo.view.wizard.importer;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import nl.fontys.limo.externaltrader.JSONImporter;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.DefaultTableModel;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.EventDataDialog;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.HubDataDialog;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.ProcedureCategoryDataDialog;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.TypeDataDialog;

/**
 * @author Matthias Brück
 */
public class ImportConflictPanel extends JPanel implements MouseListener, ActionListener, TableModelListener {

    private List<BaseEntity> entitiesToOverride, allEntitiesOld, allEntitiesNew;
    private final JTable tblEntities;
    private DefaultTableModel tblmdlEntities;
    private final JButton btnSelectAll, btnDeselectAll;
    private Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> duplicatedElements;
    private final JLabel lbConflicts, lbImport, lbDone, lbOlder;

    public ImportConflictPanel(String path) {
        if (!path.isEmpty()) {
            duplicatedElements = JSONImporter.importFromJSON(path);
            System.out.println("PATH: " + path);
        } else {
            duplicatedElements = new HashMap<>();
        }
        entitiesToOverride = new ArrayList<>();
        allEntitiesNew = new ArrayList<>();
        allEntitiesOld = new ArrayList<>();
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout("5px, pref, 5px, pref, 5px, pref:grow, 5px", "5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref, 5px, pref:grow, 5px");
        this.setLayout(layout);
        lbImport = new JLabel("Number of total entities: " + JSONImporter.getLastEntitiesInFileCount());
        this.add(lbImport, cc.xyw(2, 2, 5));
        lbDone = new JLabel("Already imported entities: " + JSONImporter.getLastDirectImportedEntitiesCount());
        this.add(lbDone, cc.xyw(2, 4, 5));
        lbConflicts = new JLabel("Number of conflicts: " + JSONImporter.getLastDuplicatedEntitieCount());
        this.add(lbConflicts, cc.xyw(2, 6, 5));
        lbOlder = new JLabel("Number of entities that were older than yours: " + JSONImporter.getLastOlderEntitieCount());
        this.add(lbOlder, cc.xyw(2, 8, 5));
        btnSelectAll = new JButton("Select All");
        btnSelectAll.addActionListener(this);
        this.add(btnSelectAll, cc.xy(2, 10));
        btnDeselectAll = new JButton("Deselect All");
        btnDeselectAll.addActionListener(this);
        this.add(btnDeselectAll, cc.xy(4, 10));

        for (List<Map.Entry<BaseEntity, BaseEntity>> list : duplicatedElements.values()) {
            for (Map.Entry<BaseEntity, BaseEntity> entity : list) {
                allEntitiesOld.add(entity.getKey());
                allEntitiesNew.add(entity.getValue());
            }
        }
        Object[][] data = new Object[allEntitiesOld.size()][5];
        for (int i = 0; i < allEntitiesOld.size(); i++) {
            data[i][0] = allEntitiesOld.get(i);
            data[i][1] = new Date(allEntitiesOld.get(i).getLastUpdate());
            data[i][2] = allEntitiesNew.get(i);
            data[i][3] = new Date(allEntitiesNew.get(i).getLastUpdate());
            data[i][4] = false;
        }
        tblmdlEntities = new DefaultTableModel(data, new String[]{"Old Entry", "Date from old", "New Entry", "Date from new", "Override old?"}, new boolean[]{false, false, false, false, true}, new Class[]{BaseEntity.class, Date.class, BaseEntity.class, Date.class, Boolean.class});
        tblmdlEntities.addTableModelListener(this);
        tblEntities = new JTable(tblmdlEntities);
        tblEntities.addMouseListener(this);
        JScrollPane scpane = new JScrollPane(tblEntities);
        this.add(scpane, cc.xyw(2, 12, 5));
    }

    public List<BaseEntity> getEntitiesToOverride() {
        return entitiesToOverride;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnDeselectAll)) {
            entitiesToOverride.clear();
            entitiesToOverride.addAll(allEntitiesNew);
            for (int i = 0; i < tblmdlEntities.getRowCount(); i++) {
                tblmdlEntities.setValueAt(true, i, 4);
            }
            tblmdlEntities.fireTableDataChanged();
        }
        if (e.getSource().equals(btnDeselectAll)) {
            entitiesToOverride.clear();
            for (int i = 0; i < tblmdlEntities.getRowCount(); i++) {
                tblmdlEntities.setValueAt(false, i, 4);
            }
            tblmdlEntities.fireTableDataChanged();
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getSource().equals(tblmdlEntities)) {
            if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntitiesNew.size()) {
                if ((boolean) (((DefaultTableModel) e.getSource()).getValueAt(tblEntities.getSelectedRow(), 4))) {
                    entitiesToOverride.add(allEntitiesNew.get(tblEntities.getSelectedRow()));
                } else {
                    entitiesToOverride.remove(allEntitiesNew.get(tblEntities.getSelectedRow()));
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntitiesOld.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                BaseEntity entity = allEntitiesOld.get(tblEntities.getSelectedRow());
                if (entity instanceof ProcedureCategory) {
                    new ProcedureCategoryDataDialog((ProcedureCategory) entity);
                }
                if (entity instanceof LegType) {
                    new TypeDataDialog((LegType) entity);
                }
                if (entity instanceof HubType) {
                    new TypeDataDialog((HubType) entity);
                }
                if (entity instanceof Hub) {
                    new HubDataDialog((Hub) entity);
                }
                if (entity instanceof Event) {
                    new EventDataDialog((Event) entity);
                }
            }
            if (tblEntities.getSelectedColumn() == 2) {
                BaseEntity entity = allEntitiesNew.get(tblEntities.getSelectedRow());
                if (entity instanceof ProcedureCategory) {
                    new ProcedureCategoryDataDialog((ProcedureCategory) entity);
                }
                if (entity instanceof LegType) {
                    new TypeDataDialog((LegType) entity);
                }
                if (entity instanceof HubType) {
                    new TypeDataDialog((HubType) entity);
                }
                if (entity instanceof Hub) {
                    new HubDataDialog((Hub) entity);
                }
                if (entity instanceof Event) {
                    new EventDataDialog((Event) entity);
                }
            }
        }
    }

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
    
    public void updateTable(String filePath){
        if (!filePath.isEmpty()) {
            duplicatedElements = JSONImporter.importFromJSON(filePath);
            System.out.println("PATH: " + filePath);
        } else {
            duplicatedElements = new HashMap<>();
        }
        entitiesToOverride = new ArrayList<>();
        allEntitiesNew = new ArrayList<>();
        allEntitiesOld = new ArrayList<>();
        for (List<Map.Entry<BaseEntity, BaseEntity>> list : duplicatedElements.values()) {
            for (Map.Entry<BaseEntity, BaseEntity> entity : list) {
                allEntitiesOld.add(entity.getKey());
                allEntitiesNew.add(entity.getValue());
            }
        }
        Object[][] data = new Object[allEntitiesOld.size()][5];
        for (int i = 0; i < allEntitiesOld.size(); i++) {
            data[i][0] = allEntitiesOld.get(i);
            data[i][1] = new Date(allEntitiesOld.get(i).getLastUpdate());
            data[i][2] = allEntitiesNew.get(i);
            data[i][3] = new Date(allEntitiesNew.get(i).getLastUpdate());
            data[i][4] = false;
        }
        tblmdlEntities = new DefaultTableModel(data, new String[]{"Old Entry", "Date from old", "New Entry", "Date from new", "Override old?"}, new boolean[]{false, false, false, false, true}, new Class[]{BaseEntity.class, Date.class, BaseEntity.class, Date.class, Boolean.class});
        tblmdlEntities.addTableModelListener(this);
        tblEntities.setModel(tblmdlEntities);
        tblmdlEntities.fireTableDataChanged();
        
        lbImport.setText("Number of total entities: " + JSONImporter.getLastEntitiesInFileCount());
        lbDone.setText("Already imported entities: " + JSONImporter.getLastDirectImportedEntitiesCount());
        lbConflicts.setText("Number of conflicts: " + JSONImporter.getLastDuplicatedEntitieCount());
        lbOlder.setText("Number of entities that are the same as yours: " + JSONImporter.getLastOlderEntitieCount());
    }
}
