package nl.fontys.sofa.limo.domain.component.serialization;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.MasterData;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;

/**
 * Deserializes the MasterData from JSON to a Java Object.
 *
 * @author Convict42
 */
public class MasterDataDeserializer implements JsonDeserializer<MasterData> {

    @Override
    public MasterData deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) {
        Gson g = GsonHelper.getInstance();
        MasterData md = new MasterData();
        JsonObject obj = je.getAsJsonObject();

        Type tLegtypes = new TypeToken<ArrayList<LegType>>() {
        }.getType();
        Type tEvent = new TypeToken<ArrayList<Event>>() {
        }.getType();
        Type tProcedure = new TypeToken<ArrayList<ProcedureCategory>>() {
        }.getType();
        Type tHubTypes = new TypeToken<ArrayList<HubType>>() {
        }.getType();
        Type tHub = new TypeToken<ArrayList<Hub>>() {
        }.getType();
        Type tProcedures = new TypeToken<ArrayList<Procedure>>() {
        }.getType();

        ArrayList<LegType> legTypeList = g.fromJson(obj.get("legtypes"), tLegtypes);
        ArrayList<BaseEntity> beLegTypeList = new ArrayList<>();
        beLegTypeList.addAll(legTypeList);

        ArrayList<Event> eventList = g.fromJson(obj.get("events"), tEvent);
        ArrayList<BaseEntity> beEventList = new ArrayList<>();
        beEventList.addAll(eventList);

        ArrayList<ProcedureCategory> catList = g.fromJson(obj.get("categories"), tProcedure);
        ArrayList<BaseEntity> beCatList = new ArrayList<>();
        beCatList.addAll(catList);

        ArrayList<HubType> hubTypeList = g.fromJson(obj.get("hubtypes"), tHubTypes);
        ArrayList<BaseEntity> beHubTypeList = new ArrayList<>();
        beHubTypeList.addAll(hubTypeList);

        ArrayList<Hub> hubList = g.fromJson(obj.get("hubs"), tHub);
        ArrayList<BaseEntity> beHubList = new ArrayList<>();
        beHubList.addAll(hubList);
        
        if(obj.get("basicProcedures") == null) {
            ArrayList<Procedure> procedureList = g.fromJson(obj.get("basicProcedures"), tProcedures);
            ArrayList<BaseEntity> beProcedureList = new ArrayList<>();
            beProcedureList.addAll(procedureList);
            md.setBasicProcedures(beProcedureList);
        }
        md.setLegtypes(beLegTypeList);
        md.setEvents(beEventList);
        md.setCategories(beCatList);
        md.setHubtypes(beHubTypeList);
        md.setHubs(beHubList);
        return md;
    }

}
