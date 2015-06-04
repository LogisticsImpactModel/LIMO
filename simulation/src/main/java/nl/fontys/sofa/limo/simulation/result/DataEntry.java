package nl.fontys.sofa.limo.simulation.result;

import static java.lang.Math.floor;

/**
 * A data entry encapsulates the data for one cost or time point in a
 * simulation. It contains the minimum, maximum and average value of that point.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
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
        this.min = floor(100 * min + 0.5) / 100;
        this.max = floor(100 * max + 0.5) / 100;
        this.avg = floor(100 * avg + 0.5) / 100;
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
