package nl.fontys.sofa.limo.domain.component;

import com.google.gson.annotations.Expose;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * Global Class containing the different catalogs of the master data.
 *
 * @author Convict42
 */
public class MasterData {

    @Expose
    private List<BaseEntity> legtypes;
    @Expose
    private List<BaseEntity> categories;
    @Expose
    private List<BaseEntity> events;
    @Expose
    private List<BaseEntity> hubtypes;
    @Expose
    private List<BaseEntity> hubs;

    public MasterData() {
    }

    /**
     * For serializing convenience creates a Map with all catalog data.
     *
     * @return Map containing all catalog entries.
     */
    public Map<String, List<BaseEntity>> getAsMap() {
        Map map = new HashMap<>();
        map.put("legtypes", legtypes);
        map.put("categories", categories);
        map.put("events", events);
        map.put("hubtypes", hubtypes);
        map.put("hubs", hubs);

        return map;
    }

    public List<BaseEntity> getLegtypes() {
        return legtypes;
    }

    public void setLegtypes(List<BaseEntity> legtypes) {
        this.legtypes = legtypes;
    }

    public List<BaseEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<BaseEntity> categories) {
        this.categories = categories;
    }

    public List<BaseEntity> getEvents() {
        return events;
    }

    public void setEvents(List<BaseEntity> events) {
        this.events = events;
    }

    public List<BaseEntity> getHubtypes() {
        return hubtypes;
    }

    public void setHubtypes(List<BaseEntity> hubtypes) {
        this.hubtypes = hubtypes;
    }

    public List<BaseEntity> getHubs() {
        return hubs;
    }

    public void setHubs(List<BaseEntity> hubs) {
        this.hubs = hubs;
    }
}
