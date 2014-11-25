package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.TypeDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 * @author Matthias Brück
 */
public class HubTypeSelectionPanel extends BaseEntitySelectionPanel<HubType> {

    public HubTypeSelectionPanel() {
        super();
    }

    @Override
    protected void initAllEntities() {
        HubTypeService hubService = Lookup.getDefault().lookup(HubTypeService.class);
        allEntities = hubService.findAll();
        component.setName("Hub Types");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                HubType entity = allEntities.get(tblEntities.getSelectedRow());
                new TypeDataDialog(entity);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(ExportWizardAction.HUB_TYPES, selectedEntities);
    }

}
