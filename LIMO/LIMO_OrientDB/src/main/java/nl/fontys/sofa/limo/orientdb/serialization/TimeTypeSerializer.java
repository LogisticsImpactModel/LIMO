package nl.fontys.sofa.limo.orientdb.serialization;

import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import nl.fontys.sofa.limo.domain.component.process.TimeType;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class TimeTypeSerializer implements OObjectSerializer<TimeType, String> {

    @Override
    public Object serializeFieldValue(Class<?> iClass, TimeType iFieldValue) {
        return iFieldValue.name();
    }

    @Override
    public Object unserializeFieldValue(Class<?> iClass, String iFieldValue) {
        return TimeType.valueOf(iFieldValue);
    }
    
}
