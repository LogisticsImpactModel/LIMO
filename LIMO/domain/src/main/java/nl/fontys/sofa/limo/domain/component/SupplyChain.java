package nl.fontys.sofa.limo.domain.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * Holder of a complete supply chain with actors, hubs, legs and events. Can be
 * stored to file and recreated from file.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class SupplyChain implements Serializable {

    private String name;
    private String filepath;
    private Hub startHub;
    private List<Actor> actors;

    public SupplyChain() {
        this.actors = new ArrayList<>();
    }

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

    public Hub getStart() {
        return startHub;
    }

    public void setStart(Hub startHub) {
        this.startHub = startHub;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    /**
     * Recreates a supply chain stored in the given file.
     *
     * @return Recreated supply chain.
     * @param file The file to open.
     */
    public static SupplyChain createFromFile(File file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            return (SupplyChain) ois.readObject();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.err);
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
        } finally {
            try {
                fis.close();
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        }

        return null;
    }

    /**
     * Saves the supply chain to a file specified at filepath.
     */
    public void saveToFile() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (filepath == null) {
                return;
            }
            fos = new FileOutputStream(filepath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
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
