package nl.fontys.sofa.limo.domain.distribution.input;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.interfaces.Copyable;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 * @param <T> Number type for this input value.
 */
public abstract class InputValue<T extends Number> implements Copyable<InputValue> {

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
     *
     * @return
     */
    public abstract Class<T> getValueType();

    @Override
    public InputValue copy() {
        InputValue copied = null;
        try {
            copied = this.getClass().getConstructor().newInstance();
            copied.setName(name);
            copied.setValue(value);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(InputValue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copied;
    }

}
