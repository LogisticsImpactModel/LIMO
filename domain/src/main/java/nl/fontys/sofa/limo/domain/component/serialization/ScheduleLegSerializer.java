/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;

/**
 *
 * @author Convict42
 */
public class ScheduleLegSerializer implements JsonSerializer<ScheduledLeg> {
        @Override
        public JsonElement serialize(ScheduledLeg src, Type typeOfSrc, JsonSerializationContext context) {
                Gson g = GsonHelper.getInstance();
                
                Leg hLeg = new Leg(src);
                hLeg.setNext(src.getNext());
                JsonObject ele1 = (JsonObject) g.toJsonTree(hLeg);
                
                ele1.add("expectedTime", new JsonPrimitive(src.getExpectedTime()));
                ele1.add("delay", new JsonPrimitive(src.getDelay()));
                ele1.add("waitingTimeLimit", new JsonPrimitive(src.getWaitingTimeLimit()));
                
                JsonArray array = new JsonArray();
                ArrayList<Long> list = (ArrayList) src.getAcceptanceTimes();
                for (Long acceptedTime : list) 
                {
                    array.add(new JsonPrimitive(acceptedTime));
                }
                ele1.add("acceptanceTimes", array);
                
                ele1.add("alternative", g.toJsonTree(src.getAlternative()));
            return ele1;
        }
    }
