package nl.fontys.sofa.limo.view.node.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.openide.nodes.PropertySupport;

/**
 * Stupid property used to be able to listen to changes in the property window.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class StupidProperty<T> extends PropertySupport.Reflection<T> {

    /**
     * Property Change Listeners for the property.
     */
    private final ArrayList<PropertyChangeListener> listeners;

    /**
     * Create new StupidProperty with reflection methods.
     *
     * @param instance Object containing value.
     * @param valueType Type of value.
     * @param getter Getter method.
     * @param setter Setter method.
     */
    public StupidProperty(Object instance, Class<T> valueType, Method getter, Method setter) {
        super(instance, valueType, getter, setter);
        listeners = new ArrayList<>();
    }

    /**
     * Create new StupidProperty with method names.
     *
     * @param instance Object containing value.
     * @param valueType Type of value.
     * @param getter Getter name.
     * @param setter Setter name.
     * @throws NoSuchMethodException Thrown if getter or setter not found.
     */
    public StupidProperty(Object instance, Class<T> valueType, String getter, String setter) throws NoSuchMethodException {
        super(instance, valueType, getter, setter);
        listeners = new ArrayList<>();
    }

    /**
     * Create new StupidProperty with property name.
     *
     * @param instance Object containing value.
     * @param valueType Type of value.
     * @param property Property name (field in class).
     * @throws NoSuchMethodException Thrownif getter or setter for property not
     * found.
     */
    public StupidProperty(Object instance, Class<T> valueType, String property) throws NoSuchMethodException {
        super(instance, valueType, property);
        listeners = new ArrayList<>();
    }

    /**
     * Set the value of the property and notify listeners.
     *
     * @param val new value.
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Override
    public void setValue(T val) throws IllegalAccessException, InvocationTargetException {
        try {
            T oldValue = getValue();
            super.setValue(val);
            firePropertyChange(oldValue, val);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw ex;
        }
    }

    /**
     * Add listener.
     *
     * @param listener Listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove listener.
     *
     * @param listener Listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify listeners of property change.
     *
     * @param oldValue Old value.
     * @param newValue New value.
     */
    protected void firePropertyChange(T oldValue, T newValue) {
        // Remove all faulty listeners (threading...)
        while (listeners.remove(null)) {
        }

        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, getName(), oldValue, newValue));
        }
    }

}
