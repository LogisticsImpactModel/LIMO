package nl.fontys.sofa.limo.orientdb;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // Load database
        OrientDBConnector.connection();
    }

    @Override
    public void close() {
        super.close();
        
        // Close database
        OrientDBConnector.close();
    }

}
