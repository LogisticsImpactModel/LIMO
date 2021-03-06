package nl.fontys.sofa.limo.simulation;

import nl.fontys.sofa.limo.simulation.task.Simulation;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.openide.util.Task;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public final class Simulator {

    private Simulator() {
        throw new UnsupportedOperationException();
    }

    public static SimulatorTask simulate(int testCaseCount, SupplyChain... supplyChains) {
        SimulatorTask simTask = new SimulatorTask();

        int i = 1;
        for (SupplyChain sc : supplyChains) {
            Simulation simulation = new Simulation(sc, testCaseCount, "" + i++);
            Task task = SimulationExecutor.post(simulation);
            simTask.addSimulation(simulation, task);
        }

        return simTask;
    }

}
