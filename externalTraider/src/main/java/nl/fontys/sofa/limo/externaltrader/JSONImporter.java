package nl.fontys.sofa.limo.externaltrader;

import nl.fontys.sofa.limo.domain.component.MasterData;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.serialization.GsonHelper;
import org.openide.util.Lookup;

/**
 * @author Matthias Brück
 * @author Dominik Kaisers
 */
public final class JSONImporter {

    /**
     * Number of entities found in the last JSON file.
     */
    private static int lastEntitiesInFileCount;

    /**
     * Number of directly imported files from the last JSON file.
     */
    private static int lastDirectImportedEntityCount;

    /**
     * Number of duplicated elements in the last JSON file. Duplicated means
     * that there is already an entity inside the database that has the same
     * unique identifier.
     */
    private static int lastDuplicatedEntityCount;

    /**
     * Number of entities in JSON file that are from an older date then the ones
     * in the database.
     */
    private static int lastOlderEntityCount;

    private JSONImporter() {
    }

    /**
     * Import database entities from a serialized .lef file.
     *
     * @param filepath Filepath where the lef file can be loaded from
     * @return Map with entities that have a conflict with the local database.
     */
    public static Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> importData(String filepath) {
        lastEntitiesInFileCount = 0;
        lastDirectImportedEntityCount = 0;
        lastDuplicatedEntityCount = 0;
        lastOlderEntityCount = 0;

        Map<String, List<BaseEntity>> allEntities = (Map<String, List<BaseEntity>>) loadFromFile(filepath);
        return checkForConflicts(allEntities);
    }

    /**
     * Load a .lef file form a filepath.
     *
     * @param filepath Filepath.
     * @return Deserialized entities.
     */
    private static Object loadFromFile(String filepath) {  
        try {
        InputStream in = new FileInputStream(filepath);
        
        Gson g = GsonHelper.getInstance();
        
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.beginArray();
            
        MasterData md = g.fromJson(reader, MasterData.class);
        reader.endArray();
        reader.close();
        
        return md.getAsMap();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }


    /**
     * Check all the entities in the map for conflicts with the local database.
     *
     * @param allEntities Entity map.
     * @return Conflict map.
     */
    private static Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> checkForConflicts(Map<String, List<BaseEntity>> allEntities) {
        Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> conflictMap = new HashMap<>();
        for (Map.Entry<String, List<BaseEntity>> entry : allEntities.entrySet()) {
            conflictMap.put(entry.getKey(), checkItems(entry.getValue(), getServiceClassForKey(entry.getKey())));
        }
        return conflictMap;
    }

    /**
     * Get the dao class for a key.
     *
     * @param key Key.
     * @return DAO class.
     */
    private static Class getServiceClassForKey(String key) {
        switch (key) {
            case "categories":
                return ProcedureCategoryService.class;
            case "legtypes":
                return LegTypeService.class;
            case "hubtypes":
                return HubTypeService.class;
            case "hubs":
                return HubService.class;
            case "events":
                return EventService.class;
            case "basicProcedures":
                return ProcedureService.class;
            default:
                return null;
        }
    }

    /**
     * Check each item in the list for conflicts.
     *
     * @param entities Items to check.
     * @param daoClass Associated DAO class.
     * @return List of conflicted items.
     */
    private static List<Map.Entry<BaseEntity, BaseEntity>> checkItems(List<BaseEntity> entities, Class serviceClass) {
        List<Map.Entry<BaseEntity, BaseEntity>> list = new ArrayList<>();
        for (BaseEntity entity : entities) {
            Map.Entry<BaseEntity, BaseEntity> item = checkItem(entity, serviceClass);
            if (item != null) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Checks a given base entity for conflicts and duplications in the
     * database.
     *
     * @param newItem Item to check.
     * @param daoClass Associated DAO class.
     * @return Map.Entry with old and new base entity or null if no
     * conflict/duplication found.
     */
    private static Map.Entry<BaseEntity, BaseEntity> checkItem(BaseEntity newItem, Class serviceClass) {
        lastEntitiesInFileCount++;

        DAO dao = (DAO) Lookup.getDefault().lookup(serviceClass);
        BaseEntity oldItem = dao.findByUniqueIdentifier(newItem.getUniqueIdentifier());
        Map.Entry<BaseEntity, BaseEntity> entry = null;

        if (oldItem != null) {
            if (oldItem.getLastUpdate() != newItem.getLastUpdate()) {
                entry = new AbstractMap.SimpleEntry<>(oldItem, newItem);
                lastDuplicatedEntityCount++;
            } else {
                lastOlderEntityCount++;
            }
        } else {
            dao.insert(newItem, false);
            lastDirectImportedEntityCount++;
        }

        return entry;
    }

    public static int getLastEntitiesInFileCount() {
        return lastEntitiesInFileCount;
    }

    public static int getLastDirectImportedEntityCount() {
        return lastDirectImportedEntityCount;
    }

    public static int getLastDuplicatedEntityCount() {
        return lastDuplicatedEntityCount;
    }

    public static int getLastOlderEntityCount() {
        return lastOlderEntityCount;
    }

}
