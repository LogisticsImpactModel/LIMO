package nl.fontys.sofa.limo.domain.distribution.input;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class IntegerInputValue extends InputValue<Integer>{

    public IntegerInputValue() {
    }

    public IntegerInputValue(String name, Integer value) {
        super(name, value);
    }

    @Override
    public Class<Integer> getValueType() {
        return Integer.class;
    }
    
}
