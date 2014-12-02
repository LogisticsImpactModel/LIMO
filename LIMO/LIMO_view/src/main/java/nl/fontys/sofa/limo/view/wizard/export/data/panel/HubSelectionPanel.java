package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import nl.fontys.sofa.limo.view.wizard.export.data.dialog.HubDataDialog;
import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 *
 * @author Matthias Brück
 */
public class HubSelectionPanel extends BaseEntitySelectionPanel<Hub> {

    public HubSelectionPanel() {
        super();
    }

    @Override
    protected void initAllEntities() {
        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        allEntities = hubService.findAll();
        component.setName("Hubs");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                Hub entity = allEntities.get(tblEntities.getSelectedRow());
                new HubDataDialog(entity);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        for (Hub selectedEntity : selectedEntities) {
            selectedEntity.setId(null);
        }
        wiz.putProperty(ExportWizardAction.HUBS, selectedEntities);
    }
}
