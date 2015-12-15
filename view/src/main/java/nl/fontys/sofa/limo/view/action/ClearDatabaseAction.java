package nl.fontys.sofa.limo.view.action;

import java.awt.event.ActionEvent;
import java.util.Set;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import nl.fontys.sofa.limo.service.provider.AbstractService;
import nl.fontys.sofa.limo.view.topcomponent.BaseEntityTopComponent;
import nl.fontys.sofa.limo.view.topcomponent.BaseEntityTopComponentWithoutDescription;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.AggregateProgressHandle;
import org.netbeans.api.progress.aggregate.ProgressContributor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

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

        if (!confirmDialog()) {
            return;
        }

//        closeCatalogTabs();
        Runnable r = new Runnable() {

            @Override
            public void run() {

                AggregateProgressHandle processHandle = AggregateProgressFactory.createHandle("Remove database", null, null, null);
                ProgressContributor emptyDatabase = AggregateProgressFactory.createProgressContributor("1");
                ProgressContributor emptyLookup = AggregateProgressFactory.createProgressContributor("2");
                processHandle.addContributor(emptyDatabase);
                processHandle.addContributor(emptyLookup);
                processHandle.start();

                emptyDatabase.start(50);
                OrientDBConnector dbConnection = OrientDBConnector.getInstance();
                dbConnection.emptyDatabase();

                emptyDatabase.progress(50);
                emptyDatabase.finish();
                emptyLookup.start(60);

                Lookup aDefault = Lookup.getDefault();

                AbstractService eventService = (AbstractService) aDefault.lookup(EventService.class);
                AbstractService hubService = (AbstractService) aDefault.lookup(HubService.class);
                AbstractService hubTypeService = (AbstractService) aDefault.lookup(HubTypeService.class);
                AbstractService procedureCategoryService = (AbstractService) aDefault.lookup(ProcedureCategoryService.class);
                AbstractService procedureService = (AbstractService) aDefault.lookup(ProcedureService.class);
                AbstractService legTypeService = (AbstractService) aDefault.lookup(LegTypeService.class);

                eventService.emptyLookup(Event.class);
                emptyLookup.progress(10);
                hubService.emptyLookup(Hub.class);
                emptyLookup.progress(10);
                hubTypeService.emptyLookup(HubType.class);
                emptyLookup.progress(10);
                procedureCategoryService.emptyLookup(ProcedureCategory.class);
                emptyLookup.progress(10);
                procedureService.emptyLookup(Procedure.class);
                emptyLookup.progress(10);
                legTypeService.emptyLookup(LegType.class);
                emptyLookup.progress(10);

                emptyLookup.finish();
                processHandle.finish();

                NotifyDescriptor end = new NotifyDescriptor(LIMOResourceBundle.getString("MASTERDATA_REMOVED"),
                        LIMOResourceBundle.getString("ACTION_COMPLETED"),
                        NotifyDescriptor.OK_CANCEL_OPTION, NotifyDescriptor.INFORMATION_MESSAGE, null, null);
                DialogDisplayer.getDefault().notify(end);
            };
        };
        RequestProcessor.getDefault().post(r);
    }

    /**
     * Close all catalog tabs to prevent problems with entities having
     * references somewhere else. Since the entities (hubs, legs etc.) are also
     * known to the services in the topcomponents it gives problems when
     * importing and deleting with these tabs open.
     */
    private void closeCatalogTabs() {

        Set<TopComponent> openTopComponents = WindowManager.getDefault().getRegistry().getOpened();

        openTopComponents.stream().filter((tc) -> (tc != null && (tc instanceof BaseEntityTopComponent
                || tc instanceof BaseEntityTopComponentWithoutDescription))).forEach((tc) -> {
                    tc.close();
                });
    }

    /**
     * Confirm dialog to prevent accidental removal.
     *
     * @return true if delete is confirmed
     */
    private boolean confirmDialog() {
        NotifyDescriptor confirm = new NotifyDescriptor(LIMOResourceBundle.getString("ARE_YOU_SURE_MASTERDATA"),
                LIMOResourceBundle.getString("CLEAR_MASTERDATA"), NotifyDescriptor.YES_NO_OPTION,
                NotifyDescriptor.INFORMATION_MESSAGE, null, null);

        Object answer = DialogDisplayer.getDefault().notify(confirm);

        if (answer.equals(NotifyDescriptor.NO_OPTION) || answer.equals(NotifyDescriptor.CLOSED_OPTION)) {
            return false;
        }

        return true;
    }
}
