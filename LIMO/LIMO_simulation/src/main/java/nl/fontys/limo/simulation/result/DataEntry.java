package nl.fontys.limo.simulation.result;

/**
 * A data entry encapsuates the data for one cost or time point in a simulation.
 * It contains the minimum, maximum and average value of that point.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DataEntry {

    private final double min;
    private final double max;
    private final double avg;

    public DataEntry(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getAvg() {
        return avg;
    }
    
}
