package nl.fontys.sofa.limo.domain.component.procedure.value;

import com.google.gson.annotations.Expose;

/**
 * Single value for a process object.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class SingleValue implements Value {

    @Expose private double value;

    public SingleValue() {
        this(0);
    }

    public SingleValue(double value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public double getMin() {
        return this.value;
    }

    @Override
    public double getMax() {
        return this.value;
    }

    @Override
    public String toString() {
        return value + "";
    }

}
