package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

/**
 *
 * @author Convict42
 */
public class ValueSerializer implements JsonSerializer<Value> {
        @Override
        public JsonElement serialize(Value src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            JsonPrimitive prim1 = new JsonPrimitive(src.getMin());
            JsonPrimitive prim2 = new JsonPrimitive(src.getMax());
            
            obj.add("Min", prim1);
            obj.add("Max", prim2);
            return obj;
        }
    }
