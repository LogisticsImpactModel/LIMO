package nl.fontys.sofa.limo.view.node.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.openide.nodes.PropertySupport;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class StupidProperty<T> extends PropertySupport.Reflection<T> {
    
    private final ArrayList<PropertyChangeListener> listeners;

    public StupidProperty(Object instance, Class<T> valueType, Method getter, Method setter) {
        super(instance, valueType, getter, setter);
        listeners = new ArrayList<>();
    }

    public StupidProperty(Object instance, Class<T> valueType, String getter, String setter) throws NoSuchMethodException {
        super(instance, valueType, getter, setter);
        listeners = new ArrayList<>();
    }

    public StupidProperty(Object instance, Class<T> valueType, String property) throws NoSuchMethodException {
        super(instance, valueType, property);
        listeners = new ArrayList<>();
    }

    @Override
    public void setValue(T val) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            T oldValue = getValue();
            super.setValue(val);
            firePropertyChange(oldValue, val);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw ex;
        }
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }
    
    protected void firePropertyChange(T oldValue, T newValue) {
        while (listeners.remove(null)) {}
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, getName(), oldValue, newValue));
        }
    }

}
