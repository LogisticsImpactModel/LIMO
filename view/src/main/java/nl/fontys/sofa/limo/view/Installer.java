package nl.fontys.sofa.limo.view;

import java.util.logging.Logger;
import nl.fontys.sofa.limo.view.status.ExceptionHandler;
import org.openide.modules.ModuleInstall;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * The installer disables the exceptions NetBeans. Instead we use the StatusBar
 * class.
 *
 * @author Pascal Lindner
 */
public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.setProperty("netbeans.exception.report.min.level", "999999");
        System.setProperty("netbeans.exception.alert.min.level", "999999");
        Logger.getLogger("").addHandler(new ExceptionHandler());

        super.restored();

        //Show Edit properties window at startup
        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

            @Override
            public void run() {
                TopComponent propertyTopComponent = WindowManager.getDefault().findTopComponent("properties");
                Mode mode = WindowManager.getDefault().findMode("properties");
                if (mode != null) {
                    mode.dockInto(propertyTopComponent);
                    propertyTopComponent.open();
                }
            }
        });
    }

    @Override
    public boolean closing() {
        return super.closing();
    }
}
