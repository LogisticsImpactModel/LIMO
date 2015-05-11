package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 * Base class for all main components of a supply chain: hubs, legs and events.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public abstract class Component extends BaseEntity {

    @Embedded
    protected List<Procedure> procedures;
    @Embedded
    protected List<Event> events;

    public Component(String name, String description) {
        super(name, description);
        this.procedures = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public Component() {
        super();
        this.procedures = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public List<Procedure> getProcedures() {
        if (procedures == null) {
            procedures = new ArrayList<>();
        }
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<Event> getEvents() {
        if (events == null) {
            events = new ArrayList<>();
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
