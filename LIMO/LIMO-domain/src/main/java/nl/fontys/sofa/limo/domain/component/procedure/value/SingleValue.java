package nl.fontys.sofa.limo.domain.component.procedure.value;

/**
 * Single value for a process object.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SingleValue implements Value {
    
    private double value;

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
    
}
