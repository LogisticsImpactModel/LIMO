package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.BasicProcedureDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias Brück
 */
public class ProcedureSelectionPanel extends BaseEntitySelectionPanel<Procedure> {

    @Override
    protected void initAllEntities() {
        ProcedureService procedureService = Lookup.getDefault().lookup(ProcedureService.class);
        allEntities = procedureService.findAll();
        component.setName(LIMOResourceBundle.getString("PROCEDURES"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                Procedure entity = allEntities.get(tblEntities.getSelectedRow());
                BasicProcedureDataDialog dialog = new BasicProcedureDataDialog(entity);
                dialog.setVisible(true);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        selectedEntities.stream().forEach((selectedEntity) -> {
            selectedEntity.setId(null);
        });
        wiz.putProperty(ExportWizardAction.PROCEDURES, selectedEntities);
    }
}
