package nl.fontys.sofa.limo.updatecenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.netbeans.api.autoupdate.InstallSupport;
import org.netbeans.api.autoupdate.OperationContainer;
import org.netbeans.api.autoupdate.OperationException;
import org.netbeans.api.autoupdate.OperationSupport;
import org.netbeans.api.autoupdate.UpdateElement;
import org.netbeans.api.autoupdate.UpdateManager;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.netbeans.api.autoupdate.UpdateUnitProvider;
import org.netbeans.api.autoupdate.UpdateUnitProviderFactory;
import org.openide.awt.NotificationDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 * @author Matthias Brück
 */
public class Installer {

    protected List<UpdateElement> install = new ArrayList<>();
    protected List<UpdateElement> update = new ArrayList<>();
    protected boolean isRestartRequested = false;

    public void searchNewAndUpdateModules() {
        for (UpdateUnitProvider provider : UpdateUnitProviderFactory.getDefault().getUpdateUnitProviders(false)) {
            try {
                provider.refresh(null, true);
            } catch (IOException ex) {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
        }
        for (UpdateUnit unit : UpdateManager.getDefault().getUpdateUnits()) {
            if (!unit.getAvailableUpdates().isEmpty()) {
                if (unit.getInstalled() == null) {
                    install.add(unit.getAvailableUpdates().get(0));
                } else {
                    update.add(unit.getAvailableUpdates().get(0));
                }
            }
        }
    }

    public OperationContainer<InstallSupport> addToContainer(OperationContainer<InstallSupport> container, List<UpdateElement> modules) {
        for (UpdateElement module : modules) {
            if (container.canBeAdded(module.getUpdateUnit(), module)) {
                OperationContainer.OperationInfo<InstallSupport> operationInfo = container.add(module);
                if (operationInfo != null) {
                    if (operationInfo.getBrokenDependencies().isEmpty()) {
                        container.add(operationInfo.getRequiredElements());
                    }
                }
            }
        }
        return container;
    }

    public void installModules(OperationContainer<InstallSupport> container) {
        try {
            InstallSupport support = container.getSupport();
            if (support != null) {

                InstallSupport.Validator validator = support.doDownload(null, true, true);
                InstallSupport.Installer installer = support.doValidate(validator, null);
                OperationSupport.Restarter restarter = support.doInstall(installer, null);
                if (restarter != null) {
                    support.doRestartLater(restarter);
                    if (isRestartRequested) {
                        NotificationDisplayer.getDefault().notify(
                                "Installed Updates",
                                ImageUtilities.loadImageIcon("com/galileo/netbeans/module/rs.png", false),
                                "Click here to restart",
                                new RestartAction(support, restarter));

                    }
                }
            }
        } catch (OperationException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    private static final class RestartAction implements ActionListener {

        private InstallSupport support;
        private OperationSupport.Restarter restarter;

        public RestartAction(InstallSupport support, OperationSupport.Restarter restarter) {
            this.support = support;
            this.restarter = restarter;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                support.doRestart(restarter, null);
            } catch (OperationException ex) {
                System.out.println(Arrays.toString(ex.getStackTrace()));
            }
        }
    }
}
