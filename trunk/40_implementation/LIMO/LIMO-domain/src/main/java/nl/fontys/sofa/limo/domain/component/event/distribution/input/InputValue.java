package nl.fontys.sofa.limo.domain.component.event.distribution.input;

import java.io.Serializable;

/**
 * Abstract super class for all possible inputs for a distribution. Handles storage of name, value
 * and type for distribution input parameters.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 * @param <T> Number type of the implementing class.
 */
public abstract class InputValue <T extends Number> implements Serializable {
    
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
    
    public abstract Class<T> getType();
    
}
