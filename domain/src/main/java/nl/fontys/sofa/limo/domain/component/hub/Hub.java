package nl.fontys.sofa.limo.domain.component.hub;

import com.google.gson.annotations.Expose;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.validation.annotations.NotNull;
import org.json.simple.JSONArray;

/**
 * Hub of a supply chain.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class Hub extends Node<Leg> {

    private static final long serialVersionUID = 1418480568142870997L;

    @Embedded
    @NotNull
    @Expose
    private Location location;
    @Embedded
    @Expose
    private Icon icon;

    public Hub() {
        super();
    }

    /**
     * Generates a {@link Hub} object from a {@link HubType} object.
     *
     * @param hubType The hub should be based on this type
     */
    public Hub(HubType hubType) {
        super();
        setName(hubType.getName());
        setDescription(hubType.getDescription());
        setIcon(hubType.getIcon());
        setEvents(hubType.getEvents());
        setProcedures(hubType.getProcedures());
    }

    /**
     * Generates a new {@link Hub}-object which contains a copy of all variables
     * of the sourceHub. The previous and next attributes are not copied.
     *
     * @param sourceHub The data of this hub is copied to the new hub
     */
    public Hub(Hub sourceHub) {
        super();
        deepOverwrite(sourceHub);
    }

    /**
     * Overwrites all attributes of the {@link Hub}-object with the attributes
     * of the sourceHub. The previous and next attributes are not copied.
     *
     * @param sourceHub
     */
    public void deepOverwrite(Hub sourceHub) {
        setId(sourceHub.getId());
        setName(sourceHub.getName());
        setDescription(sourceHub.getDescription());
        setIcon(sourceHub.getIcon());
        setEvents(sourceHub.getEvents());
        setProcedures(sourceHub.getProcedures());
        setIcon(sourceHub.getIcon());
        setUniqueIdentifier(sourceHub.getUniqueIdentifier());
        setLocation(sourceHub.getLocation());
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        firePropertyChange();
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
        firePropertyChange();
    }

    /**
     * Returns the Hub content in a String array
     *
     * @return toReturn
     */
    public JSONArray getJSONArray() {
        JSONArray toReturn = new JSONArray();
        toReturn.add(this.getId());
        toReturn.add(this.getName());
        toReturn.add(String.valueOf(this.getLastUpdate()));
        toReturn.add(String.valueOf(this.getUniqueIdentifier()));
        toReturn.add(String.valueOf(this.getDescription()));
        Location loc = this.getLocation();
        if (loc == null) {
            toReturn.add(null);
            toReturn.add(null);
            toReturn.add(null);
            toReturn.add(null);
            toReturn.add(null);
            toReturn.add(null);
        } else {
            toReturn.add(String.valueOf(loc.getState()));
            toReturn.add(String.valueOf(loc.getTown()));
            toReturn.add(String.valueOf(loc.getPostcode()));
            toReturn.add(String.valueOf(loc.getStreet()));
            toReturn.add(String.valueOf(loc.getHousenumber()));
            toReturn.add(String.valueOf(loc.getContinent().getName()));
        }
        Location.Coordinate coord = loc.getPosition();
        if (coord == null) {
            toReturn.add(null);
            toReturn.add(null);
            toReturn.add(null);
        } else {
            toReturn.add(String.valueOf(coord.getLatitude()));
            toReturn.add(String.valueOf(coord.getLongitude()));
            toReturn.add(String.valueOf(coord.getElevation()));
        }
        return toReturn;
    }
}
