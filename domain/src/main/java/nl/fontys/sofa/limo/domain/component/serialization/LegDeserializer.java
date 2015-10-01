package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 *
 * @author Convict42
 */
public class LegDeserializer implements JsonDeserializer<Leg> {
        @Override
        public Leg deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException 
        {
            JsonObject obj = json.getAsJsonObject();
            JsonElement ele1 = obj.get("Legs");
            JsonElement ele2 = obj.get("expectedTime");
            
            if(ele1 != null)
            {
                Map<Leg, Double> map = new HashMap<>();
                JsonArray array = (JsonArray) ele1;
                for (JsonElement je : array) 
                {
                    JsonObject ob1 = (JsonObject) je;
                    JsonElement elem1 = ob1.get("Leg");
                    Leg leg = deserialize(elem1, typeOfT, context);
                    double elem2 = ob1.get("Value").getAsDouble();
                    map.put(leg, elem2);                
                }
                MultiModeLeg mml = new MultiModeLeg();
                mml.setLegs(map);
                setStandard(mml, obj);
                return mml;
            }
            if(ele2 != null)
            {
                ScheduledLeg schedLeg = new ScheduledLeg();
                JsonArray array = (JsonArray) obj.get("acceptanceTimes");
                List<Long> list = new ArrayList<>();
                for (JsonElement acceptanceTime : array) {
                    list.add(acceptanceTime.getAsLong());  
                }
                schedLeg.setAcceptanceTimes(list);
                schedLeg.setDelay(obj.get("delay").getAsLong());
                schedLeg.setExpectedTime(obj.get("expectedTime").getAsLong());
                schedLeg.setWaitingTimeLimit(obj.get("waitingTimeLimit").getAsLong());
                
                JsonElement elem = obj.get("alternative");
                Leg altLeg = deserialize(elem, typeOfT, context);
                schedLeg.setAlternative(altLeg);
                
                setStandard(schedLeg, obj);
                return schedLeg;
            }
            if(ele1 == null && ele2 == null)
            {
                Leg l = new Leg();
                setStandard(l, json.getAsJsonObject());
                return l;
            }
            
            return null;
        }
        
        private void setStandard(Leg leg, JsonObject obj)
        {
            Gson g = GsonHelper.getInstance();
            Type tEvent = new TypeToken<ArrayList<Event>>(){}.getType();
            Type tProcedure = new TypeToken<ArrayList<Procedure>>(){}.getType();


            leg.setDescription(obj.get("description").getAsString());
            leg.setEvents((List<Event>) g.fromJson(obj.get("events"), tEvent));

            Icon icon = g.fromJson(obj.get("icon"), Icon.class);
            leg.setIcon(icon);

            //leg.setId(null);
            leg.setLastUpdate(obj.get("lastUpdate").getAsLong());
            leg.setName(obj.get("name").getAsString());
            if(!obj.get("next").isJsonNull()){
                leg.setNext(g.fromJson(obj.get("next"), Hub.class));
            }
            leg.setProcedures((List<Procedure>) g.fromJson(obj.get("procedures"), tProcedure));
            leg.setUniqueIdentifier(obj.get("uniqueIdentifier").getAsString());
        }
    }
