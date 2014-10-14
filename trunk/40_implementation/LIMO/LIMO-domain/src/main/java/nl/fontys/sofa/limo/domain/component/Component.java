package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.process.Procedure;

/**
 * Base class for all main components of a supply chain: hubs, legs and events.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Component extends BaseEntity {
    
    protected List<Procedure> processes;
    protected List<Event> events;

    public Component() {
        super();
        this.processes = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public List<Procedure> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Procedure> processes) {
        this.processes = processes;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
}
