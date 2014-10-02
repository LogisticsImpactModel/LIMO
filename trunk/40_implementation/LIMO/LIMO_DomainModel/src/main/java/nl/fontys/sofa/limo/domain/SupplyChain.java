package nl.fontys.sofa.limo.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.component.Leg;

public class SupplyChain implements Serializable {

    private String name;
    private Hub startHub;

    public SupplyChain() {
    }

    public SupplyChain(String name) {
        this.name = name;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hub getStartHub() {
        return this.startHub;
    }

    public void setStartHub(Hub startHub) {
        this.startHub = startHub;
    }
    // </editor-fold>

    public Leg getLeg(String identifier) {
        for (Leg leg : getAllLegs()) {
            if (leg.getIdentifier().equals(identifier)) {
                return leg;
            }
        }
        return null;
    }

    public Hub getHub(String identifier) {
        for (Hub hub : getAllHubs()) {
            if (hub.getIdentifier().equals(identifier)) {
                return hub;
            }
        }
        return null;
    }

    public List<Leg> getAllLegs() {
        if (this.startHub == null) {
            return new ArrayList<>();
        }
        ArrayList<Leg> legs = new ArrayList<>();
        Hub hub = this.startHub;
        while (hub.getOutputLeg() != null) {
            legs.add(hub.getOutputLeg());
            if (hub.getOutputLeg().getEndHub() == null) {
                break;
            }
            hub = hub.getOutputLeg().getEndHub();
        }
        return legs;
    }

    public List<Hub> getAllHubs() {
        if (this.startHub == null) {
            return new ArrayList<>();
        }
        ArrayList<Hub> hubs = new ArrayList<>();
        Hub hub = this.startHub;
        hubs.add(hub);
        while (hub.getOutputLeg() != null) {
            if (hub.getOutputLeg().getEndHub() == null) {
                break;
            }
            hub = hub.getOutputLeg().getEndHub();
            hubs.add(hub);
        }
        return hubs;
    }

    public List<Actor> getAllActors() {
        HashMap<Actor, Boolean> map = new HashMap<>();
        for (Hub hub : getAllHubs()) {
            for (Actor actor : hub.getCostResponsibilities().values()) {
                map.put(actor, true);
            }
        }
        for (Leg leg : getAllLegs()) {
            map.put(leg.getActor(), true);
        }
        ArrayList<Actor> actors = new ArrayList<>();
        actors.addAll(map.keySet());
        return actors;
    }
    
    /**
     * Opens the file at given path and tries to deserialize into a supply chain object.
     * @param filepath Path to serialized supply chain object.
     * @return SupplyChain in file. NULL if not possible to deserialize.
     */
    public static SupplyChain createFromFile(String filepath) {
        SupplyChain supplyChain = null;
        try {
            FileInputStream fin = new FileInputStream(filepath);
            ObjectInputStream oin = new ObjectInputStream(fin);
            supplyChain = (SupplyChain) oin.readObject();
            oin.close();
            fin.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SupplyChain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SupplyChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return supplyChain;
    }
    
    /**
     * Saves the given supply chain to the given file path.
     * @param supplyChain SupplyChain to serialize.
     * @param filepath Path to where the serialized object should be saved.
     */
    public static void saveToFile(SupplyChain supplyChain, String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(supplyChain);
            oos.close();
            fos.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SupplyChain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SupplyChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
