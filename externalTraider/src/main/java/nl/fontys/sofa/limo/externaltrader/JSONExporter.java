package nl.fontys.sofa.limo.externaltrader;

import nl.fontys.sofa.limo.domain.component.MasterData;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.serialization.GsonHelper;

/**
 * JSON Exporter takes care of converting a list w/ BaseEntities to a JSON
 * object, which is then written to a file at a specified filepath
 *
 * @author Matthias Brück
 * @author Dominik Kaisers
 */
public final class JSONExporter {

    private JSONExporter() {
    }

    /**
     * Export the given entities to a .lef file.
     *
     * @param allEntitites Entities to serialize.
     * @param filepath Filepath.
     */
    public static void export(Map<String, List<BaseEntity>> allEntitites, String filepath) {
        // Remove local database IDs
        allEntitites.values().stream().forEach((entityList) -> {
            nullifyIds(entityList);
        });

        // Serialize to file
        File file = new File(filepath);
        file.delete();
        MasterData md = new MasterData();
        md.setLegtypes(allEntitites.get("legtypes"));
        md.setCategories(allEntitites.get("categories"));
        md.setHubs(allEntitites.get("hubs"));
        md.setHubtypes(allEntitites.get("hubtypes"));
        md.setEvents(allEntitites.get("events"));
        md.setBasicProcedures(allEntitites.get("basicProcedures"));
        writeToFile(md, filepath);
    }

    /**
     * Sets the if of the given entities to null for export.
     *
     * @param entities Entitites to nullify.
     */
    private static void nullifyIds(List<BaseEntity> entities) {
        entities.stream().forEach((entity) -> {
            entity.setId(null);
        });
    }

    /**
     * Serialize an object to the specified location on disk.
     *
     * @param obj Object to serialize.
     * @param filepath Filepath where file should be saved
     */
    private static void writeToFile(MasterData md, String filepath) {
        Gson g = GsonHelper.getInstance();
        OutputStream out = null;
        try {
            if (filepath == null) {
                return;
            }
            out = new FileOutputStream(filepath, true);
            try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"))) {
                writer.setIndent("  ");
                writer.beginArray();
                g.toJson(md, MasterData.class, writer);
                writer.endArray();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
