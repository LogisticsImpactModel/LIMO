package nl.fontys.sofa.limo.domain.value;

public class SingleValue implements Value {

    private double value;

    public SingleValue() {
        this(0);
    }

    public SingleValue(double value) {
        this.value = value;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
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
    // </editor-fold>

    @Override
    public Value copy() {
        SingleValue copied = new SingleValue();
        copied.setValue(value);
        return copied;
    }
}
