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
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Component extends BaseEntity {

    @Embedded
    protected List<Procedure> procedures;
    @Embedded
    protected List<Event> events;

    public Component() {
        super();
        this.procedures = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Add an event to the component.
     *
     * @param event - the event to be added.
     * @return boolean - true if successfully added.
     */
    public boolean addEvent(Event event) {
        if (event != null) {
            boolean success = events.add(event);
            return success;
        }
        return false;
    }

    /**
     * Remove an event from the component.
     *
     * @param event - the event to be removed.
     * @return boolean - true if successfully removed.
     */
    public boolean removeEvent(Event event) {
        if (event != null) {
            boolean success = events.remove(event);
            return success;
        }
        return false;
    }

    /**
     * Add a procedure to the component.
     *
     * @param procedure - the procedure to be added.
     * @return boolean - true if successfully added.
     */
    public boolean addProcedure(Procedure procedure) {
        if (procedure != null) {
            boolean success = procedures.add(procedure);
            return success;
        }
        return false;
    }

    /**
     * Remove a procedure from the component.
     *
     * @param procedure - the procedure to be removed.
     * @return boolean - true if successfully removed.
     */
    public boolean removeProcedure(Procedure procedure) {
        if (procedure != null) {
            boolean success = procedures.remove(procedure);
            return success;
        }
        return false;
    }
}
