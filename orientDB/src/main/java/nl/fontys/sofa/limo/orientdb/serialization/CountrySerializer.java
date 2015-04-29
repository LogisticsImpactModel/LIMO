package nl.fontys.sofa.limo.orientdb.serialization;

import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import nl.fontys.sofa.limo.domain.component.hub.SerializableCountry;

/**
 * Serializer for countries.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class CountrySerializer implements OObjectSerializer<SerializableCountry, String> {

    @Override
    public Object serializeFieldValue(Class<?> iClass, SerializableCountry iFieldValue) {
        return iFieldValue.getIsoAlpha3();
    }

    @Override
    public Object unserializeFieldValue(Class<?> iClass, String iFieldValue) {
        return SerializableCountry.getInstance(iFieldValue);
    }

}
