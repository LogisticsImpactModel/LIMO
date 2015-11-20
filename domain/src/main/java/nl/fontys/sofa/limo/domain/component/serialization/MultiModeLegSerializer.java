package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;

/**
 * Serializes a Leg from a Java Object to JSON.
 *
 * @author Convict42
 */
public class MultiModeLegSerializer implements JsonSerializer<MultiModeLeg> {

    @Override
    public JsonElement serialize(MultiModeLeg src, Type typeOfSrc, JsonSerializationContext context) {
        Gson g = GsonHelper.getInstance();
        Leg hLeg = new Leg(src);
        hLeg.setNext(src.getNext());
        JsonObject ele1 = (JsonObject) g.toJsonTree(hLeg);

        JsonArray array = new JsonArray();

        Map<Leg, Double> mapToSeri = src.getLegs();

        mapToSeri.entrySet().stream().map((entrySet) -> {
            Leg key = entrySet.getKey();
            Double value = entrySet.getValue();
            JsonObject ele2 = new JsonObject();
            ele2.add("Leg", g.toJsonTree(key));
            ele2.addProperty("Value", value);
            return ele2;
        }).forEach((ele2) -> {
            array.add(ele2);
        });
        ele1.add("Legs", array);
        return ele1;
    }
}
