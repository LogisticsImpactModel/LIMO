package nl.fontys.sofa.limo.domain.distribution.input;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DoubleInputValue extends InputValue<Double>{

    public DoubleInputValue() {
    }

    public DoubleInputValue(String name, Double value) {
        super(name, value);
    }

    @Override
    public Class<Double> getValueType() {
        return Double.class;
    }
    
}
