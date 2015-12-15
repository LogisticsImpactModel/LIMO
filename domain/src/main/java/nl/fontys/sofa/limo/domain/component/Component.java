package nl.fontys.sofa.limo.domain.component;

import com.google.gson.annotations.Expose;
import java.beans.PropertyChangeEvent;
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
    @Expose
    protected List<Procedure> procedures;
    @Embedded
    @Expose
    protected List<Event> events;

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
        return new ArrayList<>(procedures);
    }

    public void setProcedures(List<Procedure> procedures) {
        procedures.stream().filter((p) -> (!this.procedures.contains(p))).forEach((p) -> {
            fireAddProcedure(p);
        });

        this.procedures.stream().filter((p) -> (!procedures.contains(p))).forEach((p) -> {
            fireRemoveProcedure(p);
        });

        this.procedures = procedures;
    }

    public List<Event> getEvents() {
        if (events == null) {
            events = new ArrayList<>();
        }
        return new ArrayList<>(events);
    }

    public void setEvents(List<Event> events) {
        events.stream().filter((e) -> (!this.events.contains(e))).forEach((e) -> {
            fireAddEvent(e);
        });

        this.events.stream().filter((e) -> (!events.contains(e))).forEach((e) -> {
            fireRemoveEvent(e);
        });

        this.events = new ArrayList<>(events);
    }

    private void fireAddEvent(Event event) {
        PropertyChangeEvent e = new PropertyChangeEvent(this, "ADD_EVENT", null, event);
        listeners.stream().forEach((listern) -> {
            listern.propertyChange(e);
        });

    }

    private void fireRemoveEvent(Event event) {
        PropertyChangeEvent e = new PropertyChangeEvent(this, "REMOVE_EVENT", event, null);
        listeners.stream().forEach((listern) -> {
            listern.propertyChange(e);
        });

    }

    private void fireAddProcedure(Procedure procedure) {
        PropertyChangeEvent e = new PropertyChangeEvent(this, "ADD_PROCEDURE", null, procedure);
        listeners.stream().forEach((listern) -> {
            listern.propertyChange(e);
        });

    }

    private void fireRemoveProcedure(Procedure procedure) {
        PropertyChangeEvent e = new PropertyChangeEvent(this, "REMOVE_PROCEDURE", procedure, null);
        listeners.stream().forEach((listern) -> {
            listern.propertyChange(e);
        });

    }

}
