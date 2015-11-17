package nl.fontys.sofa.limo.domain.component.leg;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Leg of supply chain.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class Leg extends Node<Hub> {

    @Expose private static final long serialVersionUID = 4146579601096201593L;

    @Expose private Icon icon;

    public Leg() {
    }

    /**
     * Generates a {@link Leg} object from a {@link LegType} object.
     *
     * @param legType The leg should be based on this type
     */
    public Leg(LegType legType) {
        super();
        setName(legType.getName());
        setDescription(legType.getDescription());
        setIcon(legType.getIcon());
        setEvents(legType.getEvents());
        setProcedures(legType.getProcedures());
    }

    /**
     * Generates a new {@link Leg}-object which contains a copy of all variables
     * of the sourceLeg. The previous and next attributes are not copied.
     *
     * @param sourceLeg The data of this leg is copied to the new hub
     */
    public Leg(Leg sourceLeg) {
        super();
        deepOverwrite(sourceLeg);
    }

    /**
     * Overwrites all attributes of the {@link Leg}-object with the attributes
     * of the sourceLeg. The previous and next attributes are not copied.
     *
     * @param sourceLeg
     */
    public void deepOverwrite(Leg sourceLeg) {
        setId(sourceLeg.getId());
        setName(sourceLeg.getName());
        setDescription(sourceLeg.getDescription());
        setIcon(sourceLeg.getIcon());
        setEvents(sourceLeg.getEvents());
        setProcedures(sourceLeg.getProcedures());
        setIcon(sourceLeg.getIcon());
        setUniqueIdentifier(sourceLeg.getUniqueIdentifier());
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

}
