package nl.fontys.limo.simulation;

import nl.fontys.limo.simulation.task.Simulation;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.openide.util.Task;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public final class Simulator {

    private Simulator() {
        throw new UnsupportedOperationException();
    }

    public static SimulatorTask simulate(int testCaseCount, SupplyChain... supplyChains) {
        SimulatorTask simTask = new SimulatorTask();

        for (SupplyChain sc : supplyChains) {
            Simulation simulation = new Simulation(sc, testCaseCount);
            Task task = SimulationExecutor.post(simulation);
            simTask.addSimulation(simulation, task);
        }

        return simTask;
    }

}
