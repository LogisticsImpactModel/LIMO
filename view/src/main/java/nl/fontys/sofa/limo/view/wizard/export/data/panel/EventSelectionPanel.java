package nl.fontys.sofa.limo.view.wizard.export.data.panel;

import java.awt.event.MouseEvent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.export.ExportWizardAction;
import nl.fontys.sofa.limo.view.wizard.export.data.dialog.EventDataDialog;
import org.openide.WizardDescriptor;
import org.openide.util.Lookup;

/**
 * Specific data selection panel for events.
 *
 * @author Matthias Brück
 */
public class EventSelectionPanel extends BaseEntitySelectionPanel<Event> {

    @Override
    protected void initAllEntities() {
        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        allEntities = eventService.findAll();
        component.setName(LIMOResourceBundle.getString("EVENTS"));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (tblEntities.getSelectedRow() >= 0 && tblEntities.getSelectedRow() < allEntities.size()) {
            if (tblEntities.getSelectedColumn() == 0) {
                Event entity = allEntities.get(tblEntities.getSelectedRow());
                EventDataDialog dialog = new EventDataDialog(entity);
                dialog.setVisible(true);
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        selectedEntities.stream().forEach((selectedEntity) -> {
            selectedEntity.setId(null);
        });
        wiz.putProperty(ExportWizardAction.EVENTS, selectedEntities);
    }
}
