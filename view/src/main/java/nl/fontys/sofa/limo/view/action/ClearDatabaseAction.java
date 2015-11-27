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
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

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

        Lookup lkp = Utilities.actionsGlobalContext();
        List<DAO> list = new ArrayList();

        EventService eventService = Lookup.getDefault().lookup(EventService.class);
        HubService hubService = Lookup.getDefault().lookup(HubService.class);
        HubTypeService hubTypeService = Lookup.getDefault().lookup(HubTypeService.class);
        LegTypeService legTypeService = Lookup.getDefault().lookup(LegTypeService.class);
        ProcedureCategoryService procedureCategoryService = Lookup.getDefault().lookup(ProcedureCategoryService.class);
        ProcedureService procedureService = Lookup.getDefault().lookup(ProcedureService.class);

        list.add(eventService);
        list.add(hubService);
        list.add(hubTypeService);
        list.add(legTypeService);
        list.add(procedureCategoryService);
        list.add(procedureService);
        
        System.out.println("-");

        list.stream().filter((service) -> (service != null)).forEach((service) -> {
            List<BaseEntity> entries = service.findAll();
            System.out.println("entries.size: "+ entries.size() +"flippo");
            entries.stream().forEach((entry) -> {
                service.delete(entry);
            });
        }); 

        
//Calling all services seperately and removing their contents in the database
        //Events
//        if (eventService != null) {
//            List<Event> eventList = eventService.findAll();
//            eventList.stream().forEach((event) -> {
//                eventService.delete(event);
//            });
//        }
//
//        //Hub templates
//        if (hubService != null) {
//            List<Hub> hubList = hubService.findAll();
//            hubList.stream().forEach((hub) -> {
//                hubService.delete(hub);
//            });
//        }
//
//        //HubTypes
//        if (hubTypeService != null) {
//            List<HubType> hubTypeList = hubTypeService.findAll();
//            hubTypeList.stream().forEach((hubType) -> {
//                hubTypeService.delete(hubType);
//            });
//        }
//
//        //legTypes
//        if (legTypeService != null) {
//            List<LegType> legTypeList = legTypeService.findAll();
//            legTypeList.stream().forEach((legType) -> {
//                legTypeService.delete(legType);
//            });
//        }
//
//        //Procedure category
//        if (procedureCategoryService != null) {
//            List<ProcedureCategory> procedureCategoryList = procedureCategoryService.findAll();
//            procedureCategoryList.stream().forEach((procedureCategory) -> {
//                procedureCategoryService.delete(procedureCategory);
//            });
//        }
//
//        //Procedures
//        if (procedureService != null) {
//            List<Procedure> procedureList = procedureService.findAll();
//            procedureList.stream().forEach((procedure) -> {
//                procedureService.delete(procedure);
//            });
//        }
    }
}
