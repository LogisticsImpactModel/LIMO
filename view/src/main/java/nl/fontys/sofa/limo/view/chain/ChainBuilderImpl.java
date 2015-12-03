package nl.fontys.sofa.limo.view.chain;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.serialization.GsonHelper;

/**
 * Implementation of the {@link nl.fontys.sofa.limo.view.chain.ChainBuilder}
 * interface.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainBuilderImpl implements ChainBuilder {

    private final SupplyChain chain;
    private final List<Hub> hubList;
    private final List<ActionListener> listener;

    /**
     * Constructor for the ChainBuilderImpl. It creates a new
     * {@link nl.fontys.sofa.limo.domain.component.SupplyChain} and a list of
     * hubs.
     */
    public ChainBuilderImpl() {
        chain = new SupplyChain();
        hubList = new ArrayList<>();
        listener = new ArrayList<>();
    }

    @Override
    public void addListener(ActionListener listener) {
        this.listener.add(listener);
    }

    @Override
    public void removeListener(ActionListener listener) {
        this.listener.remove(listener);
    }

    @Override
    public void modify() {
        ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_FIRST, "modify");
        listener.parallelStream().forEach((l) -> {
            l.actionPerformed(e);
        });
    }

    @Override
    public List<Hub> getHubList() {
        return hubList;
    }

    @Override
    public void addHub(Hub hub) {
        hubList.add(hub);
        modify();

    }

    @Override
    public void removeHub(Hub hub) {
        hubList.remove(hub);
        if (hub.getNext() != null) {
            disconnectLeg(hub.getNext());
        }
        if (hub.getPrevious() != null) {
            disconnectLeg(hub.getPrevious());
        }
        modify();
    }

    @Override
    public int getNumberOfHubs() {
        return hubList.size();
    }

    @Override
    public Hub getStartHub() {
        return chain.getStartHub();
    }

    @Override
    public void setStartHub(Hub hub) {
        chain.setStartHub(hub);
    }

    @Override
    public void connectHubsByLeg(Hub source, Leg connection, Hub target) {
        source.setNext(connection);
        connection.setPrevious(source);
        connection.setNext(target);
        target.setPrevious(connection);
        modify();
    }

    @Override
    public void disconnectLeg(Leg leg) {
        leg.removeNext();
        leg.removePrevious();
        modify();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks whether there are at least 2 hubs and if every hub is connected by
     * a leg.
     */
    @Override
    public boolean validate() {
        int hubCount = 1;
        Node currentNode = getStartHub();
        if (currentNode == null || getNumberOfHubs() == 1) {
            return false;
        }
        while (currentNode != null) {
            currentNode = currentNode.getNext();
            if (currentNode instanceof Hub) {
                hubCount++;
            }
        }
        return hubCount == getNumberOfHubs();
    }

    @Override
    public SupplyChain getSupplyChain() {
        return chain;
    }
    
    /**
     * Saves the supply chain to a file specified at filepath.
     * @throws java.io.IOException
     */
    @Override
    public void saveToFile(String filepath) throws IOException {
        OutputStream out = new FileOutputStream(filepath);
        Gson g = GsonHelper.getInstance();
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"))) {
            writer.setIndent("  ");
            writer.beginArray();
            
            ArrayList<Hub> toSaveList = new ArrayList<>((ArrayList<Hub>) hubList);
            System.out.println(hubList.size());
            for(Hub hub : hubList) {
                if(hub.getPrevious() != null)
                {
                    System.out.println(hub.getName());
                    toSaveList.remove(hub);
                }
            }
            System.out.println(hubList.size());
            for (Hub hub : toSaveList) {
               System.out.println(hub.getName());
               g.toJson(hub, Hub.class, writer); 
            }
            writer.endArray();
        }
    }
}
