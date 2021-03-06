package nl.fontys.sofa.limo.simulation.util;

/**
 * MathuUtil incorporates methods that help in simulating the supply chains.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public final class MathUtil {

    MathUtil() throws InstantiationException {
        throw new InstantiationException("Instances of this type are forbidden.");
    }

    /**
     * Calculates the cumulative moving average of the given values.
     *
     * @param oldAvg Old average.
     * @param oldCount Count of numbers used for old average.
     * @param newValue Value to add to the average.
     * @return New average.
     */
    public static double getCumulativeMovingAverage(double oldAvg, double oldCount, double newValue) {
        return (newValue + (oldCount * oldAvg)) / (oldCount + 1);
    }

}
