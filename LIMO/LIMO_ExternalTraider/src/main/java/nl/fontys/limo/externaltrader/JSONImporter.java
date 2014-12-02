package nl.fontys.limo.externaltrader;

import com.orientechnologies.orient.core.record.impl.ODocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openide.util.Lookup;

/**
 * @author Matthias Brück
 */
public final class JSONImporter {

    /**
     * Number of entities found in the last JSON file.
     */
    private static int lastEntitiesInFileCount;
    /**
     * Number of directly imported files from the last JSON file.
     */
    private static int lastDirectImportedEntitiesCount;
    /**
     * Number of dublicated elements in the from the last JSON file. Duplicated
     * means that there is already an entity inside the database that has the
     * same unique identifier.
     */
    private static int lastDuplicatedEntitiedCount;

    private JSONImporter() {
    }

    public static Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> importFromJSON(String filepath) {
        lastEntitiesInFileCount = 0;
        lastDirectImportedEntitiesCount = 0;
        lastDuplicatedEntitiedCount = 0;
        Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> importedFiles = new HashMap<>();
        File fileToImport = new File(filepath);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileToImport))) {
            String textInLine = reader.readLine();
            String textInFile = "";
            while (textInLine != null) {
                textInFile = textInFile.concat(textInLine);
            }
            importedFiles = getMapFromText(importedFiles, textInFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return importedFiles;
    }

    private static Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> getMapFromText(Map<String, List<Map.Entry<BaseEntity, BaseEntity>>> importedFiles, String textInFile) {
        JSONObject rootObject = new JSONObject(textInFile);
        for (String key : rootObject.keySet()) {
            JSONArray items = rootObject.getJSONArray(key);
            List<Map.Entry<BaseEntity, BaseEntity>> list = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                lastEntitiesInFileCount++;
                JSONObject item = items.getJSONObject(i);
                Map.Entry entry = null;
                switch (key) {
                    case "categories":
                        entry = JSONImporter.<ProcedureCategory>checkItem(item, ProcedureCategoryDAO.class, new ProcedureCategory());
                        break;
                    case "legtypes":
                        entry = JSONImporter.<LegType>checkItem(item, LegTypeDAO.class, new LegType());
                        break;
                    case "hubtypes":
                        entry = JSONImporter.<HubType>checkItem(item, HubTypeDAO.class, new HubType());
                        break;
                    case "hubs":
                        entry = JSONImporter.<Hub>checkItem(item, HubDAO.class, new Hub());
                        break;
                    case "events":
                        entry = JSONImporter.<Event>checkItem(item, EventDAO.class, new Event());
                        break;
                    default:
                        break;
                }
                if (entry != null) {
                    list.add(entry);
                }
            }
            if (list.size() > 0) {
                importedFiles.put(key, list);
            }
        }
        return importedFiles;
    }

    /**
     * Checks if the item is already in the database and returns an abstract map
     * that holds both, the new and the old item. If the item is already inside
     * the database null is returned.
     *
     * @param <T> The classtype that extends BaseEntity.
     * @param item The JSONObject that holds the imported entity.
     * @param daoclass The DAO Class to fetch a possible match out of the
     * database.
     * @param newT A new Object of T to fill the data from the possible
     * databasematch in.
     * @return An Map.Entry<T, T> which is either null when there was no match
     * inside the database or is filled with the database entry and the imported
     * entry.
     */
    private static <T extends BaseEntity> Map.Entry<T, T> checkItem(JSONObject item, Class daoclass, T newT) {
        ODocument doc = new ODocument().fromJSON(item.toString());
        String uuid = doc.field("uniqueIdentifier", String.class);
        AbstractMap.SimpleEntry<T, T> entry = null;
        DAO pcDAO = (DAO) Lookup.getDefault().lookup(daoclass);
        T old = (T) pcDAO.findByUniqueIdentifier(uuid);
        if (old != null) {
            T newC = (T) OrientDBConnector.connection().stream2pojo(doc, newT, "*:-1");
            if (old.getLastUpdate() != newC.getLastUpdate()) {
                entry = new AbstractMap.SimpleEntry<>(old, newC);
                lastDuplicatedEntitiedCount++;
            }
        } else {
            doc.save();
            lastDirectImportedEntitiesCount++;
        }
        return entry;
    }

    public static int getLastEntitiesInFileCount() {
        return lastEntitiesInFileCount;
    }

    public static int getLastDirectImportedEntitiesCount() {
        return lastDirectImportedEntitiesCount;
    }

    public static int getLastDuplicatedEntitiedCount() {
        return lastDuplicatedEntitiedCount;
    }
}
