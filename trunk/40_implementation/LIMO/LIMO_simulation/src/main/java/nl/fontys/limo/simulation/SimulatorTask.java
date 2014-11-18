package nl.fontys.limo.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.limo.simulation.task.Simulation;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SimulatorTask implements TaskListener{
    
    private final Map<Task, Simulation> simulations;
    private final List<SimulationResult> results;
    
    public SimulatorTask() {
        simulations = new HashMap<>();
        results = new ArrayList<>();
    }
    
    public boolean isDone() {
        return results.size() == simulations.size();
    }
    
    public double getProgress() {
        return (double)results.size() / (double)simulations.size();
    }
    
    public List<SimulationResult> getResults() {
        return results;
    }
    
    protected void addSimulation(Simulation simulation, Task task) {
        simulations.put(task, simulation);
    }

    @Override
    public void taskFinished(Task task) {
        results.add(simulations.get(task).getResult());
    }
    
}
