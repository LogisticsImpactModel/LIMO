package nl.fontys.sofa.limo.view;

import java.util.logging.Logger;
import nl.fontys.sofa.limo.view.status.ExceptionHandler;
import org.openide.modules.ModuleInstall;

/**
 * The installer disables the exceptions NetBeans would show the user. Instead
 * we use the StatusBar class.
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
    }

}
