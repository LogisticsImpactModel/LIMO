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
import java.util.HashMap;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
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
        
    private static class LegDeserializer implements JsonDeserializer<Leg> {
        @Override
        public Leg deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException 
        {
            JsonObject obj = json.getAsJsonObject();
            JsonArray ele1 = obj.get("legs").getAsJsonArray();
            
            if(false)
            {
                Map<Leg, Double> map = new HashMap<>();
                for (JsonElement je : ele1) {
                }
                return new MultiModeLeg();
            }
            if(false)
            {
                return new ScheduledLeg();
            }
            else
            {
                return new Leg();
            }
        }
    }
    private static class LegSerializer implements JsonSerializer<Leg> {
        @Override
        public JsonElement serialize(Leg src, Type typeOfSrc, JsonSerializationContext context) {
            System.out.println("BLAAARRGGGGHHH???????: " + String.valueOf(src instanceof MultiModeLeg));
            Gson g = getGson();
            if(src instanceof MultiModeLeg)
            {                
                Leg hLeg = new Leg(src);
                System.out.println("BLAAARRGGGGHHH!!!!!: " + String.valueOf(hLeg instanceof MultiModeLeg));
                JsonObject ele1 = (JsonObject) g.toJsonTree(hLeg);
                
                JsonArray jArray = new JsonArray();
                MultiModeLeg multiLeg = (MultiModeLeg) src;
                Map<Leg,Double> map = multiLeg.getLegs();
                for (Map.Entry<Leg, Double> entrySet : map.entrySet()) {
                    Leg key = entrySet.getKey();
                    Double value = entrySet.getValue(); 
                    
                    JsonObject jObj1 = new JsonObject();
                    jObj1.add("key", g.toJsonTree(key));
                    jObj1.add("value", g.toJsonTree(value));
                    jArray.add(jObj1);
                }
                ele1.add("legs", jArray);
                return ele1;
            }
            return g.toJsonTree(src);
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
            gsonB.registerTypeAdapter(Leg.class, new LegSerializer());
            gsonB.registerTypeAdapter(Leg.class, new LegDeserializer());
            gson = gsonB.excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        }
        
        return gson;
    }
}
