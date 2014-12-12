package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.HubDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 * Specific data selection panel for hubs.
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
        component.setName(LIMOResourceBundle.getString("HUBS"));
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
