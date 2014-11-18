package nl.fontys.limo.simulation.task;

import java.util.HashMap;
import java.util.Map;
import nl.fontys.limo.simulation.SimulationExecutor;
import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Simulation implements Runnable, TaskListener{
    
    private final SupplyChain supplyChain;
    private final Map<Task, TestCase> testCaseTasks;
    private final int testCaseCount;
    private final SimulationResult result;
    
    private int finishedCount;

    public Simulation(SupplyChain supplyChain, int testCaseCount) {
        this.supplyChain = supplyChain;
        this.testCaseCount = testCaseCount;
        this.testCaseTasks = new HashMap<>();
        this.result = new SimulationResult(supplyChain);
        this.finishedCount = 0;
    }
    
    /**
     * Get the percentage of finished test cases.
     * @return Percentage of test cases finished.
     */
    public double getProgress() {
        return (double)finishedCount / (double)testCaseCount;
    }

    public SimulationResult getResult() {
        return result;
    }

    @Override
    public void run() {
        testCaseTasks.clear();
        finishedCount = 0;
        
        for (int i = 0; i < testCaseCount; i++) {
            // Submit test cases and attach this as listener
            TestCase testCase = new TestCase(supplyChain);
            Task task = SimulationExecutor.post(testCase);
            task.addTaskListener(this);
            
            // Put into map
            testCaseTasks.put(task, testCase);
        }
    }

    @Override
    public void taskFinished(Task task) {
        finishedCount++;
        result.addTestCaseResult(testCaseTasks.get(task).getResult());
    }
    
}
