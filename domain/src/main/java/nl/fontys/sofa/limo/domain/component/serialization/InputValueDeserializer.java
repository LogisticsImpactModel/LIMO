/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.IntegerInputValue;
import org.openide.util.Exceptions;

/**
 * Deserializes an InputValue from JSON to a Java Object.
 *
 * @author Convict42
 */
public class InputValueDeserializer implements JsonDeserializer<InputValue> {

    @Override
    public InputValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {

        JsonObject obj = json.getAsJsonObject();
        String propType = obj.get("propType").getAsString();
        try {
            Class c = Class.forName(propType);
            InputValue input = (InputValue) c.newInstance();
            input.setName(obj.get("Name").getAsString());
            if (input instanceof DoubleInputValue) {
                input.setValue(obj.get("Value").getAsDouble());
            }
            if (input instanceof IntegerInputValue) {
                input.setValue(obj.get("Value").getAsInt());
            }
            return input;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }

        return null;
    }
}
