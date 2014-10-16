package nl.fontys.sofa.limo.domain.component.procedure.value;

import java.util.Random;

/**
 * Single value for a process object.
 * 
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class RangeValue implements Value {
    
    /**
     * Random Number Generator.
     */
    private static Random rng;

    private double min;
    private double max;

    public RangeValue() {
        this(0, 1);
    }

    public RangeValue(double max) {
        this(0, max);
    }

    public RangeValue(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public double getValue() {
        return getRandomNumber(this.min, this.max);
    }

    @Override
    public double getMin() {
        return this.min;
    }

    @Override
    public double getMax() {
        return this.max;
    }

    /**
     * Returns a random double in the specified range.
     *
     * @param min Minimal value.
     * @param max Maximal value.
     * @return Double between min and max.
     */
    private double getRandomNumber(double min, double max) {
        if (rng == null) {
            rng = new Random();
        }

        return min + (max - min) * rng.nextDouble();
    }
    
}
