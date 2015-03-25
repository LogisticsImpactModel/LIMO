package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.ProcedureDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias Brück
 */
public class ProcedureCategorySelectionPanel extends BaseEntitySelectionPanel<ProcedureCategory> {

    @Override
    protected void initAllEntities() {
        ProcedureCategoryService hubService = Lookup.getDefault().lookup(ProcedureCategoryService.class);
        allEntities = hubService.findAll();
        component.setName(LIMOResourceBundle.getString("PROCEDURE_CATEGORIES"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                ProcedureCategory entity = allEntities.get(tblEntities.getSelectedRow());
                ProcedureDataDialog dialog = new ProcedureDataDialog(entity);
                dialog.setVisible(true);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        for (ProcedureCategory selectedEntity : selectedEntities) {
            selectedEntity.setId(null);
        }
        wiz.putProperty(ExportWizardAction.CATEGORIES, selectedEntities);
    }
}
