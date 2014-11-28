package nl.fontys.limo.simulation;

import nl.fontys.limo.simulation.task.Simulation;
import nl.fontys.limo.simulation.task.TestCase;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public final class SimulationExecutor {

    private static RequestProcessor simulationProcessor;
    private static RequestProcessor testCaseProcessor;

    private static boolean shuttingDown = false;

    private SimulationExecutor() {
        throw new UnsupportedOperationException();
    }

    public static Task post(Simulation simulation) {
        if (simulationProcessor == null) {
            simulationProcessor = new RequestProcessor("SExecutor", 1);
        }

        return simulationProcessor.post(simulation, 0, Thread.MAX_PRIORITY);
    }

    public static Task post(TestCase testCase) {
        if (testCaseProcessor == null) {
            testCaseProcessor = new RequestProcessor("TCExecutor", Runtime.getRuntime().availableProcessors());
        }

        return testCaseProcessor.post(testCase, 1000, Thread.NORM_PRIORITY);
    }

    public static void shutdown() {
        if (simulationProcessor != null) {
            simulationProcessor.shutdown();
            simulationProcessor = null;
        }
        if (testCaseProcessor != null) {
            testCaseProcessor.shutdown();
            testCaseProcessor = null;
        }
        shuttingDown = true;
    }

    public static boolean isShutdown() {
        return simulationProcessor.isShutdown() && testCaseProcessor.isShutdown();
    }

    public static boolean isShuttingDown() {
        shuttingDown = !shuttingDown ? false : !isShutdown();
        return shuttingDown;
    }

}
