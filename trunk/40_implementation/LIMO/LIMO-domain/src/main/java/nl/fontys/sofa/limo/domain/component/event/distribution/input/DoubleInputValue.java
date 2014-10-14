package nl.fontys.sofa.limo.domain.component.event.distribution.input;

/**
 * Input value of type Double.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class DoubleInputValue extends InputValue<Double> {

    public DoubleInputValue() {
    }

    public DoubleInputValue(String name, Double value) {
        super(name, value);
    }

    @Override
    public Class<Double> getType() {
        return Double.class;
    }
    
}
