package nl.fontys.sofa.limo.domain.component;

import com.google.gson.Gson;
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
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.serialization.GsonHelper;
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
        
        Gson g = GsonHelper.getInstance();
        
        SupplyChain supplyChain;
            try (JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"))) {
                reader.beginArray();
                supplyChain = g.fromJson(reader, SupplyChain.class);
                reader.endArray();
            }
        return supplyChain;
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    /**
     * Saves the supply chain to a file specified at filepath.
     * @throws java.io.IOException
     */
    public void saveToFile() throws IOException {
        OutputStream out = new FileOutputStream(filepath);
        Gson g = GsonHelper.getInstance();
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"))) {
            writer.setIndent("  ");
            writer.beginArray();
            g.toJson(this, SupplyChain.class, writer);
            writer.endArray();
        }
    }
}