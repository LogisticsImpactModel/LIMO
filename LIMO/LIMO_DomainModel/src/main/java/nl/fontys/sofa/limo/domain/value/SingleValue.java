package nl.fontys.sofa.limo.domain.value;

public class SingleValue implements Value {

    private final double value;

    public SingleValue() {
        this(0);
    }

    public SingleValue(double value) {
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
