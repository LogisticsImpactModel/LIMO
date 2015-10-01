package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;

/**
 *
 * @author Convict42
 */
public class DistributionSerializer implements JsonSerializer<Distribution> {
    @Override
    public JsonElement serialize(Distribution src, Type typeOfSrc, JsonSerializationContext context) {
            Gson g = GsonHelper.getInstance();
            JsonObject toReturn = new JsonObject();

            JsonArray jArray = new JsonArray();
            Map<String, InputValue> map = src.getInputValues();
            for (Map.Entry<String, InputValue> entrySet : map.entrySet()) 
            {   
                JsonObject obj = new JsonObject();
                obj.addProperty("Name", entrySet.getKey());
                JsonElement ele = g.toJsonTree(entrySet.getValue());
                obj.add("Value", ele);
                jArray.add(obj);
            }
            
            toReturn.add("inputValues", jArray);
            toReturn.addProperty("propType", src.getClass().getName());

        return toReturn;
    }
}
