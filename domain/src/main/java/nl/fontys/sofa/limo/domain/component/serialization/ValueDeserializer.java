package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;

/**
 *
 * @author Convict42
 */
public class ValueDeserializer implements JsonDeserializer<Value> {
        @Override
        public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException 
        {
            JsonObject obj = json.getAsJsonObject();
            double ele1 = obj.get("Min").getAsDouble();
            double ele2 = obj.get("Max").getAsDouble();
            
            if(ele1 == ele2)
            {
                return new SingleValue(ele1);
            }
            else
            {
                return new RangeValue(ele1, ele2);
            }
        }
    }
