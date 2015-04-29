package nl.fontys.sofa.limo.orientdb.serialization;

import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import nl.fontys.sofa.limo.domain.component.hub.Continent;

/**
 * Serializer for continents.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class ContinentSerializer implements OObjectSerializer<Continent, String> {

    @Override
    public Object serializeFieldValue(Class<?> iClass, Continent iFieldValue) {
        return iFieldValue.name();
    }

    @Override
    public Object unserializeFieldValue(Class<?> iClass, String iFieldValue) {
        return Continent.valueOf(iFieldValue);
    }

}
