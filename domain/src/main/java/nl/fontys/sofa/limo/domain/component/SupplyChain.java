package nl.fontys.sofa.limo.domain.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.value.RangeValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.SingleValue;
import nl.fontys.sofa.limo.domain.component.procedure.value.Value;
import org.openide.util.Exceptions;

/**
 * Holder of a complete supply chain with actors, hubs, legs and events. Can be
 * stored to file and recreated from file.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class SupplyChain implements Serializable {

    @Expose private static final long serialVersionUID = 1185813427289144002L;

    @Expose private String name;
    @Expose private String filepath;
    /**
     * The supply chain does only contain the start hub because via the start
     * hub it can get the complete supply chain due the properties of a
     * LinkedList.
     */
    @Expose private Hub startHub;
    
    private static Gson gson;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Hub getStartHub() {
        return startHub;
    }

    public void setStartHub(Hub startHub) {
        this.startHub = startHub;
    }

    /**
     * Recreates a supply chain stored in the given file.
     *
     * @return Recreated supply chain.
     * @param file The file to open.
     */
    public static SupplyChain createFromFile(File file) {
        try {
        InputStream in = new FileInputStream(file);
        
        Gson g = getGson();
        
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.beginArray();
        SupplyChain supplyChain = g.fromJson(reader, SupplyChain.class);
        reader.endArray();
        reader.close();
        return supplyChain;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    /**
     * Saves the supply chain to a file specified at filepath.
     */
    public void saveToFile() throws IOException {
        OutputStream out = new FileOutputStream(filepath);
        Gson g = getGson();
        System.out.println("meow?: " + g);
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writer.beginArray();
        g.toJson(this, SupplyChain.class, writer);
        writer.endArray();
        writer.close();
    }
    
    private static class ValueDeserializer implements JsonDeserializer<Value> {
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
    
    private static class IconDeserializer implements JsonDeserializer<Icon> {
        @Override
        public Icon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException 
        {
            Gson g = getGson();
            JsonObject obj = json.getAsJsonObject();
            byte[] ele1 = g.fromJson(obj.get("data"), byte[].class);
            String ele2 = obj.get("imageType").getAsString();
            
            return new Icon(ele1, ele2);
        }
    }
    
    private static void setStandard(Leg leg, JsonObject obj, Boolean withNext)
    {
        Gson g = getGson();
        Type tEvent = new TypeToken<ArrayList<Event>>(){}.getType();
        Type tProcedure = new TypeToken<ArrayList<Procedure>>(){}.getType();
        
        
        leg.setDescription(obj.get("description").getAsString());
        leg.setEvents((List<Event>) g.fromJson(obj.get("events"), tEvent));
        
        Icon icon = g.fromJson(obj.get("icon"), Icon.class);
        leg.setIcon(icon);
 
        //leg.setId(null);
        leg.setLastUpdate(obj.get("lastUpdate").getAsLong());
        leg.setName(obj.get("name").getAsString());
        if(withNext){
            leg.setNext(g.fromJson(obj.get("next"), Hub.class));
        }
        leg.setProcedures((List<Procedure>) g.fromJson(obj.get("procedures"), tProcedure));
        leg.setUniqueIdentifier(obj.get("uniqueIdentifier").getAsString());
    }
    
    private static class LegDeserializer implements JsonDeserializer<Leg> {
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
                setStandard(mml, obj, true);
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
                
                setStandard(schedLeg, obj, true);
                return schedLeg;
            }
            if(ele1 == null && ele2 == null)
            {
                Leg l = new Leg();
                setStandard(l, json.getAsJsonObject(), false);
                return l;
            }
            
            return null;
        }
    }
    private static class MultiModeLegSerializer implements JsonSerializer<MultiModeLeg> {
        @Override
        public JsonElement serialize(MultiModeLeg src, Type typeOfSrc, JsonSerializationContext context) {
                Gson g = getGson();
                Leg hLeg = new Leg(src);
                hLeg.setNext(src.getNext());
                JsonObject ele1 = (JsonObject) g.toJsonTree(hLeg);
                
                JsonArray array = new JsonArray();
                
                Map<Leg, Double> mapToSeri = src.getLegs();
                
                for (Map.Entry<Leg, Double> entrySet : mapToSeri.entrySet()) 
                {
                    Leg key = entrySet.getKey();
                    Double value = entrySet.getValue();
                    JsonObject ele2 = new JsonObject();
                    ele2.add("Leg", g.toJsonTree(key));
                    ele2.addProperty("Value", value);
                    array.add(ele2);
                }
                ele1.add("Legs", array);
            return ele1;
        }
    }
    
    private static class ScheduleLegSerializer implements JsonSerializer<ScheduledLeg> {
        @Override
        public JsonElement serialize(ScheduledLeg src, Type typeOfSrc, JsonSerializationContext context) {
                Gson g = getGson();
                
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
    
    private static class ValueSerializer implements JsonSerializer<Value> {
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
    
    private static Gson getGson(){
        if(gson == null)
        {
            GsonBuilder gsonB = new GsonBuilder();
            gsonB.registerTypeAdapter(Value.class, new ValueSerializer());
            gsonB.registerTypeAdapter(Value.class, new ValueDeserializer());
            gsonB.registerTypeAdapter(MultiModeLeg.class, new MultiModeLegSerializer());
            gsonB.registerTypeAdapter(ScheduledLeg.class, new ScheduleLegSerializer());
            gsonB.registerTypeAdapter(Leg.class, new LegDeserializer());
            gsonB.registerTypeAdapter(Icon.class, new IconDeserializer());
            gson = gsonB.excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        }
        
        return gson;
    }
}
