package nl.fontys.sofa.limo.externaltrader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.BaseEntity;

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
        for (List<BaseEntity> entityList : allEntitites.values()) {
            nullifyIds(entityList);
        }

        // Serialize to file
        writeToFile(allEntitites, filepath);
    }

    /**
     * Sets the if of the given entities to null for export.
     *
     * @param entities Entitites to nullify.
     */
    private static void nullifyIds(List<BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            entity.setId(null);
        }
    }

    /**
     * Serialize an object to the specified location on disk.
     *
     * @param obj Object to serialize.
     * @param filepath Filepath.
     */
    private static void writeToFile(Object obj, String filepath) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (filepath == null) {
                return;
            }
            fos = new FileOutputStream(filepath, false);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                fos.close();
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
