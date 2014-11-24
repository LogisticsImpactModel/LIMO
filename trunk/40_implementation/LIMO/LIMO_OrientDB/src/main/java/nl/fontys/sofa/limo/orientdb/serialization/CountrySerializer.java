package nl.fontys.sofa.limo.orientdb.serialization;

import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import com.sksamuel.gaia.Country;

/**
 * Serializer for countries.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class CountrySerializer implements OObjectSerializer<Country, String> {

    @Override
    public Object serializeFieldValue(Class<?> iClass, Country iFieldValue) {
        return iFieldValue.getIsoAlpha3();
    }

    @Override
    public Object unserializeFieldValue(Class<?> iClass, String iFieldValue) {
        return Country.getInstance(iFieldValue);
    }
    
}
