/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.DoubleInputValue;
import nl.fontys.sofa.limo.domain.component.event.distribution.input.InputValue;
import org.openide.util.Exceptions;

/**
 *
 * @author Convict42
 */
public class DistributionDeserializer implements JsonDeserializer<Distribution> {
    @Override
    public Distribution deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        
        Gson g = GsonHelper.getInstance();
        JsonObject obj = json.getAsJsonObject();
        String propType = obj.get("propType").getAsString();
        
        try {
            Class c = Class.forName(propType);
            Distribution distribution = (Distribution) c.newInstance();
            
            Map<String,InputValue> map = new HashMap<>();
            
            JsonArray jArray = obj.get("inputValues").getAsJsonArray();
            for (JsonElement ele : jArray) 
            {
                JsonObject inputValObject = ele.getAsJsonObject();
                String key = inputValObject.get("Name").getAsString();
                InputValue inputVal = g.fromJson(inputValObject.get("Value"), InputValue.class);
                map.put(key, inputVal);
            }
            distribution.setInputValues(map);
            
            return distribution;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            }
        
        return null;
    }
}
