package nl.fontys.limo.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.limo.simulation.task.Simulation;
import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.AggregateProgressHandle;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SimulatorTask implements TaskListener {

    private final Map<Task, Simulation> simulations;
    private final List<SimulationResult> results;
    private Set<SimulatorTaskListener> list;
    private final AggregateProgressHandle processHandle;

    public SimulatorTask() {
        simulations = new HashMap<>();
        results = new ArrayList<>();
        processHandle = AggregateProgressFactory.createHandle("Simulating...", null, null, null);
        processHandle.start();
    }

    public boolean isDone() {
        return results.size() == simulations.size();
    }

    public double getProgress() {
        return (double) results.size() / (double) simulations.size();
    }

    public List<SimulationResult> getResults() {
        return results;
    }

    protected void addSimulation(Simulation simulation, Task task) {
        processHandle.addContributor(simulation.getProgressContributor());
        task.addTaskListener(this);
        simulations.put(task, simulation);
    }

    @Override
    public void taskFinished(Task task) {
        results.add(simulations.get(task).getResult());

        if (isDone()) {
            processHandle.finish();
            if (list != null) {
                for (SimulatorTaskListener stl : list) {
                    stl.taskFinished(this);
                }
            }
        }
    }

    /**
     * Add a listener to the task. The listener will be called once the task
     * {@link #isFinished()}. In case the task is already finished, the listener
     * is called immediately.
     *
     * @param l the listener to add
     */
    public void addTaskListener(SimulatorTaskListener l) {
        synchronized (this) {
            if (list == null) {
                list = new HashSet<>();
            }
            list.add(l);
        }

        if (isDone()) {
            l.taskFinished(this);
        }
    }

    /**
     * Remove a listener from the task.
     *
     * @param l the listener to remove
     */
    public synchronized void removeTaskListener(SimulatorTaskListener l) {
        if (list == null) {
            return;
        }

        list.remove(l);
    }

}
