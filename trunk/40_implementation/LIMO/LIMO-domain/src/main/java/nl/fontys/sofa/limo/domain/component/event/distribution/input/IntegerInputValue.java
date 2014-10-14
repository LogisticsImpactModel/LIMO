package nl.fontys.sofa.limo.domain.component.event.distribution.input;

/**
 * Input value of type Integer.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class IntegerInputValue extends InputValue<Integer> {

    public IntegerInputValue() {
    }

    public IntegerInputValue(String name, Integer value) {
        super(name, value);
    }

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }
    
}
