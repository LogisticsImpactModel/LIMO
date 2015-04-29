package nl.fontys.sofa.limo.orientdb.serialization;

import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import nl.fontys.sofa.limo.domain.component.event.ExecutionState;

/**
 * Serializer for event execution states.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class ExecutionStateSerializer implements OObjectSerializer<ExecutionState, String>{

    @Override
    public Object serializeFieldValue(Class<?> iClass, ExecutionState iFieldValue) {
        return iFieldValue.name();
    }

    @Override
    public Object unserializeFieldValue(Class<?> iClass, String iFieldValue) {
        return ExecutionState.valueOf(iFieldValue);
    }
    
}
