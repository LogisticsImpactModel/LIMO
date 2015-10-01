package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;

/**
 *
 * @author Convict42
 */
public class InputValueSerializer implements JsonSerializer<InputValue> {
    @Override
    public JsonElement serialize(InputValue src, Type typeOfSrc, JsonSerializationContext context) {
            Gson g = GsonHelper.getInstance();
            JsonObject toReturn = new JsonObject();

            toReturn.addProperty("Name", src.getName());
            toReturn.addProperty("Value", src.getValue());
            toReturn.addProperty("propType", src.getClass().getName());

        return toReturn;
    }
}
