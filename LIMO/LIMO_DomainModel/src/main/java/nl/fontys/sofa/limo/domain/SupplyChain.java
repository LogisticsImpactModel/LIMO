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
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.domain.component.EventExecutionStateDependency;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.domain.exceptions.LogicalOperationException;

public class SupplyChain implements Serializable {

    private String name;
    private Hub startHub;

    public SupplyChain() {
    }

    public SupplyChain(String name) {
        this.name = name;
    }
    
    public SupplyChain(Hub startHub) {
        this.startHub = startHub;
    }
    
    public SupplyChain(String name, Hub startHub) {
        this.name = name;
        this.startHub = startHub;
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

    /**
     * Adds a hub to the supply chain. If the starthub is "null" e.g. there is
     * no start hub, the start hub will get set to the given one.
     * Throws an LogicalOperationException when
     * the last object is a hub as well.
     *
     * @param hub The hub to add.
     * @throws LogicalOperationException Gets thrown when the last object is
     * also a hub.
     */
    public void addHub(Hub hub) throws LogicalOperationException {
        Component lastComponent = getLastComponent();
        if (lastComponent == null) {
            startHub = hub;
        } else {
            if (lastComponent instanceof Hub) {
                throw new LogicalOperationException("Last Element is a Hub."
                        + "Can't append a Hub.");
            }
            ((Leg) lastComponent).setEndHub(hub);
        }
    }

    /**
     * Adds a leg to the supply chain. Throws an LogicalOperationException when
     * the last object is a leg as well.
     *
     * @param leg The leg to add.
     * @throws LogicalOperationException Gets thrown when the last object is
     * also a leg.
     */
    public void addLeg(Leg leg) throws LogicalOperationException {
        Component lastComponent = getLastComponent();
        if (lastComponent == null) {
            throw new LogicalOperationException("There are no elements in the"
                    + "supply chain. First object has to be a hub.");
        }
        if (lastComponent instanceof Leg) {
            throw new LogicalOperationException("Last Element is a Leg. Can't"
                    + "append a Leg.");
        }
        ((Hub) lastComponent).setOutputLeg(leg);
    }

    /**
     * Adds an event to the supply chain. If the parent component is an event,
     * the dependency that is given is taken. If the parent is a hub or a leg,
     * the dependency get set to independent and the event is added to the hub
     * or leg. If the parent component is an event but the dependency is
     * "independent" the event gets added to the top level parent (hub or leg)
     * and the dependency gets set to independet.
     *
     * @param event The event that should be added to the supply chain.
     * @param dependency The dependency of the event towards the parent
     * component.
     * @param parentComponent The parent component of the event.
     */
    public void addEvent(Event event, EventExecutionStateDependency dependency, Component parentComponent) {
        if (parentComponent instanceof Event) {
            if (dependency.equals(EventExecutionStateDependency.INDEPENDENT)) {
                Component parent = event.getParent();
                while (!(parent instanceof Leg) || !(parent instanceof Hub)) {
                    parent = ((Event) parent).getParent();
                }
                event.setDependency(EventExecutionStateDependency.INDEPENDENT);
                parent.addEvent(event);
            } else {
                ((Event) parentComponent).addEvent(event, dependency);
            }
        } else if ((parentComponent instanceof Hub) || (parentComponent instanceof Leg)) {
            parentComponent.addEvent(event);
            event.setDependency(EventExecutionStateDependency.INDEPENDENT);
            System.out.println("Event dependency set to INDEPENDENT because it"
                    + "got added to a Hub or Leg");
        }
    }

    /**
     * Searches a leg with a specific identifier.
     *
     * @param identifier The identifier.
     * @return The leg with the identifier or null if there is no such leg.
     */
    public Leg getLeg(String identifier) {
        for (Leg leg : getAllLegs()) {
            if (leg.getIdentifier().equals(identifier)) {
                return leg;
            }
        }
        return null;
    }

    /**
     * Searches a hub with a specific identifier.
     *
     * @param identifier The identifier.
     * @return the hub with the identifier or null if there is no such hub.
     */
    public Hub getHub(String identifier) {
        for (Hub hub : getAllHubs()) {
            if (hub.getIdentifier().equals(identifier)) {
                return hub;
            }
        }
        return null;
    }

    /**
     * Iterates through the supply chain and returns all legs.
     *
     * @return A list containing all legs.
     */
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

    /**
     * Iterates through the supply chain and returns all hubs.
     *
     * @return A list containing all hubs.
     */
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

    /**
     * Iterates through the supply chain to find all actors. Each actor will get
     * returned only once. ATM there are only actors of legs and hubs returned.
     *
     * @return A List containing all actors that take part in the supply chain.
     */
    public List<Actor> getAllActors() {
        /*
         *
         *ADD ACTORS OF EVENTS AS WELL??? NEEDS TO GET CHECKED!
         * 
         */
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
     * Iterates throught the components to find the last one, which gets
     * returneSd.
     *
     * @return The last component in the supply chain. Can be null if there is
     * no start hub specified!
     */
    public Component getLastComponent() {
        Component lastObject = startHub;
        Component help = startHub;
        while (help != null) {
            lastObject = help;
            if (help instanceof Hub) {
                help = ((Hub) help).getOutputLeg();
            } else {
                help = ((Leg) help).getEndHub();
            }
        }
        return lastObject;
    }

    /**
     * Opens the file at given path and tries to deserialize into a supply chain
     * object.
     *
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
     *
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
