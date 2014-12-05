package nl.fontys.limo.simulation;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public boolean closing() {
        if (!SimulationExecutor.isShutdown() && !SimulationExecutor.isShuttingDown()) {
            SimulationExecutor.shutdown();
        }

        return SimulationExecutor.isShutdown();
    }

}
