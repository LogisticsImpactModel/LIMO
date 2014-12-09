package nl.fontys.sofa.limo.simulation.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import nl.fontys.sofa.limo.simulation.SimulationExecutor;
import nl.fontys.sofa.limo.simulation.result.SimulationResult;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.ProgressContributor;
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

    private final ProgressContributor progressContributor;
    private final Object pcLock = new Object();

    public Simulation(SupplyChain supplyChain, int testCaseCount, String id) {
        this.supplyChain = supplyChain;
        this.testCaseCount = testCaseCount;
        this.testCaseTasks = new ConcurrentHashMap<>();
        this.result = new SimulationResult(supplyChain);
        this.finishedCount = new AtomicInteger(0);
        this.scPool = new LinkedBlockingQueue<>();
        this.progressContributor = AggregateProgressFactory.createProgressContributor(id);
    }

    public boolean isDone() {
        return finishedCount.get() == testCaseCount;
    }

    /**
     * Get the percentage of finished test cases.
     *
     * @return Percentage of test cases finished.
     */
    public double getProgress() {
        return isDone() ? 1.0d : (finishedCount.doubleValue() / (double) testCaseCount);
    }

    public SimulationResult getResult() {
        return result;
    }

    public ProgressContributor getProgressContributor() {
        return progressContributor;
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
        progressContributor.start(testCaseCount);
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
            SupplyChain sc = null;

            synchronized (scPool) {
                while (scPool.isEmpty()) {
                    try {
                        scPool.wait();
                    } catch (InterruptedException ex) {
                        // OK Keep waiting
                        System.out.println("Simulation thread interrupted while waiting for supply chain. Keep waiting.");
                    }
                }

                sc = scPool.poll();
                if (sc == null) {
                    continue;
                }
            }

            // Submit test cases and attach this as listener
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

        TestCase tc = testCaseTasks.remove(task); // Check for null tc
        result.addTestCaseResult(tc.getResult());

        synchronized (scPool) {
            scPool.offer(tc.getResult().getSupplyChain());
            scPool.notify();
        }

        synchronized (pcLock) {
            if (finishedCount.get() % 1000 == 0) {
                progressContributor.progress(finishedCount.get() + " of " + testCaseCount + " test cases run.", finishedCount.get());
                System.out.println(finishedCount.get() + " of " + testCaseCount + " test cases run.");
            }
            if (isDone()) {
                progressContributor.finish();
                System.out.println("-> Simulation finished!");
                System.out.println("     -> Gathered " + result.getTestCaseCount() + " test cases.");
            }
        }
    }

}
