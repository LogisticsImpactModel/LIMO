package nl.fontys.sofa.limo.simulation.result;

/**
 * A data entry encapsulates the data for one cost or time point in a
 * simulation. It contains the minimum, maximum and average value of that point.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DataEntry {

    private final Double min;
    private final Double max;
    private final Double avg;

    public DataEntry() {
        min = null;
        max = null;
        avg = 0d;
    }

    public DataEntry(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getAvg() {
        return avg;
    }

}
