package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.TypeDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias Brück
 */
public class LegTypeSelectionPanel extends BaseEntitySelectionPanel<LegType> {

    public LegTypeSelectionPanel() {
        super();
    }

    @Override
    protected void initAllEntities() {
        LegTypeService hubService = Lookup.getDefault().lookup(LegTypeService.class);
        allEntities = hubService.findAll();
        component.setName(LIMOResourceBundle.getString("LEG_TYPES"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                LegType entity = allEntities.get(tblEntities.getSelectedRow());
                new TypeDataDialog(entity);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        for (LegType selectedEntity : selectedEntities) {
            selectedEntity.setId(null);
        }
        wiz.putProperty(ExportWizardAction.LEG_TYPES, selectedEntities);
    }
}
