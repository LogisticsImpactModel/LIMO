package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

/*
This action removes all loaded templates (hubs, legs categories etc.) that are loaded
in the application.
*/

@ActionID(
        category = "File",
        id = "nl.fontys.sofa.limo.view.action.ClearDatabaseAction"
)
@ActionRegistration(
        iconBase = "icons/gui/Delete_Database-16.png",
        displayName = "#CTL_ClearDatabaseAction"
)
@ActionReference(path = "Menu/Master Data", position = 57, separatorAfter = 58)
@Messages("CTL_ClearDatabaseAction=Remove loaded templates")
public final class ClearDatabaseAction extends AbstractAction {

    public ClearDatabaseAction() {
        putValue(NAME, LIMOResourceBundle.getString("CLEAR_DATABASE"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        List<DAO> list = new ArrayList();

        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        HubTypeService hubTypeService = Lookup.getDefault().lookup(HubTypeService.class);
        ProcedureCategoryService procedureCategoryService = Lookup.getDefault().lookup(ProcedureCategoryService.class);
        ProcedureService procedureService = Lookup.getDefault().lookup(ProcedureService.class);
        LegTypeService legTypeService = Lookup.getDefault().lookup(LegTypeService.class);

        list.add(eventService);
        list.add(hubService);
        list.add(hubTypeService);
        list.add(procedureCategoryService);
        list.add(procedureService);
        list.add(legTypeService);

        list.stream().filter((service) -> (service != null)).forEach((service) -> {

            List<BaseEntity> entries = service.findAll();

            entries.stream().filter((entry) -> (entry != null)).forEach((entry) -> {
                service.delete(entry);
            });
        });
    }
}
