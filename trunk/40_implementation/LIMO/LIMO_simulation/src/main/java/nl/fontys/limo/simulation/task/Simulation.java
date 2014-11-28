package nl.fontys.limo.simulation.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import nl.fontys.limo.simulation.SimulationExecutor;
import nl.fontys.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Simulation implements Runnable, TaskListener {

    private final SupplyChain supplyChain;
    private final Map<org.openide.util.Task, TestCase> testCaseTasks;
    private final int testCaseCount;
    private final SimulationResult result;

    private AtomicInteger finishedCount;

    private final BlockingQueue<SupplyChain> scPool;

    public Simulation(SupplyChain supplyChain, int testCaseCount) {
        this.supplyChain = supplyChain;
        this.testCaseCount = testCaseCount;
        this.testCaseTasks = new HashMap<>();
        this.result = new SimulationResult(supplyChain);
        this.finishedCount = new AtomicInteger(0);
        this.scPool = new LinkedBlockingQueue<>();
    }

    /**
     * Get the percentage of finished test cases.
     *
     * @return Percentage of test cases finished.
     */
    public double getProgress() {
        return finishedCount.doubleValue() / (double) testCaseCount;
    }

    public SimulationResult getResult() {
        return result;
    }

    /**
     * Deep copies a supply chain using in memory serialization.
     *
     * @param supplyChain Supply chain to copy.
     * @return Copied supply chain.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private SupplyChain deepCopy(SupplyChain supplyChain) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(supplyChain);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return (SupplyChain) ois.readObject();
    }

    @Override
    public void run() {
        testCaseTasks.clear();
        finishedCount = new AtomicInteger(0);

        // Create supply chain pool
        for (int i = 0; i < 5; i++) {
            try {
                SupplyChain copy = deepCopy(supplyChain);
                scPool.offer(copy);
            } catch (IOException | ClassNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        if (scPool.isEmpty()) {
            return;
        }

        int i = 0;
        while (i < testCaseCount) {
            // Submit test cases and attach this as listener
            SupplyChain sc = scPool.poll();
            if (sc == null) {
                continue;
            }

            TestCase testCase = new TestCase(sc);
            Task task = SimulationExecutor.post(testCase);
            task.addTaskListener(this);

            // Put into map
            testCaseTasks.put(task, testCase);
            i++;
            task.schedule(0);
        }
    }

    @Override
    public void taskFinished(org.openide.util.Task task) {
        finishedCount.incrementAndGet();
        result.addTestCaseResult(testCaseTasks.get(task).getResult());
        scPool.offer(testCaseTasks.get(task).getResult().getSupplyChain());
    }

}
