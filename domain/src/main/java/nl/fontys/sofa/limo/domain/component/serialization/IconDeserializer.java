package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import nl.fontys.sofa.limo.domain.component.Icon;

/**
 * Deserializes an Icon from JSON to a Java Object.
 *
 * @author Convict42
 */
public class IconDeserializer implements JsonDeserializer<Icon> {

    @Override
    public Icon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson g = GsonHelper.getInstance();
        JsonObject obj = json.getAsJsonObject();
        byte[] ele1 = g.fromJson(obj.get("data"), byte[].class);
        String ele2 = obj.get("imageType").getAsString();

        return new Icon(ele1, ele2);
    }
}
