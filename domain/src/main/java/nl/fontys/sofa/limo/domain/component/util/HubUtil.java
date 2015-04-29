/*
 *  Created by Mike de Roode
 */
package nl.fontys.sofa.limo.domain.component.util;

import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.type.HubType;

/**
 *
 * @author Mike
 */
public class HubUtil {

    /**
     * Generates a {@link Hub} object from a {@link HubType} object.
     *
     * @param hubType The hub should be based on this type
     * @return Generated hub
     */
    public static Hub generateHubFromHubType(HubType hubType) {
        Hub hub = new Hub();
        hub.setName(hubType.getName());
        hub.setDescription(hubType.getDescription());
        hub.setIcon(hubType.getIcon());
        hub.setEvents(hubType.getEvents());
        hub.setProcedures(hubType.getProcedures());

        return hub;
    }

    /**
     * Generates a new {@link Hub}-object which contains a copy of all variables
     * of the sourceHub. The previous and next attributes are not copied.
     *
     * @param sourceHub The data of this hub is copied to the new hub
     * @return Generated hub which contains a copy of all variables of the
     * sourceHub
     */
    public static Hub deepCopy(Hub sourceHub) {
        Hub hub = new Hub();
        hub.setId(sourceHub.getId());
        hub.setName(sourceHub.getName());
        hub.setDescription(sourceHub.getDescription());
        hub.setIcon(sourceHub.getIcon());
        hub.setEvents(new ArrayList(sourceHub.getEvents()));
        hub.setProcedures(new ArrayList(sourceHub.getProcedures()));
        hub.setIcon(sourceHub.getIcon());
        hub.setUniqueIdentifier(sourceHub.getUniqueIdentifier());
        hub.setLocation(new Location(sourceHub.getLocation()));
        return hub;
    }

    /**
     * Overwrites all attributes of the targetHub with the attributes of the
     * sourceHub. The previous and next attributes are not copied.
     *
     * @param sourceHub
     * @param targetHub
     */
    public static void deepOverwrite(Hub sourceHub, Hub targetHub) {
        targetHub.setId(sourceHub.getId());
        targetHub.setName(sourceHub.getName());
        targetHub.setDescription(sourceHub.getDescription());
        targetHub.setIcon(sourceHub.getIcon());
        targetHub.setEvents(sourceHub.getEvents());
        targetHub.setProcedures(sourceHub.getProcedures());
        targetHub.setIcon(sourceHub.getIcon());
        targetHub.setUniqueIdentifier(sourceHub.getUniqueIdentifier());
        targetHub.setLocation(sourceHub.getLocation());
    }
}
