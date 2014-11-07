package nl.fontys.sofa.limo.updatecenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.netbeans.api.autoupdate.InstallSupport;
import org.netbeans.api.autoupdate.OperationContainer;
import org.netbeans.api.autoupdate.UpdateElement;
import org.openide.util.RequestProcessor;

/**
 * @author Matthias Brück
 */
public class AutoInstaller implements Runnable {

    private static final Logger LOG = Logger.getLogger(AutoInstaller.class.getName());

    @Override
    public void run() {
        RequestProcessor.getDefault().post(new AutoInstallerImpl(), 1000);
    }

    private static final class AutoInstallerImpl extends Installer implements Runnable {

        @Override
        public void run() {
            searchNewAndUpdateModules();
            OperationContainer<InstallSupport> installContainer = addToContainer(OperationContainer.createForInstall(), install);
            installModules(installContainer);
            OperationContainer<InstallSupport> updateContainer = addToContainer(OperationContainer.createForUpdate(), update);
            installModules(updateContainer);
        }
    }
}
