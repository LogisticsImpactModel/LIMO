package nl.fontys.sofa.limo.domain.component;

import com.google.gson.annotations.Expose;
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

    private static final long serialVersionUID = 7595293546266887990L;

    @Embedded
    @Expose protected List<Procedure> procedures;
    @Embedded
    @Expose protected List<Event> events;

    public Component(String name, String description) {
        super(name, description);
        this.procedures = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public Component(Component component) {
        this(component.getName(), component.getDescription());
        this.procedures = component.getProcedures();
        this.events = component.getEvents();
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
