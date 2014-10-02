package nl.fontys.sofa.limo.domain.distribution.input;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 * @param <T> Number type for this input value.
 */
public abstract class InputValue<T extends Number> {
    
    protected String name;
    protected T value;

    public InputValue() {
    }

    public InputValue(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    /**
     * Returns the Class of the value.
     * @return 
     */
    public abstract Class<T> getValueType();
    
}
