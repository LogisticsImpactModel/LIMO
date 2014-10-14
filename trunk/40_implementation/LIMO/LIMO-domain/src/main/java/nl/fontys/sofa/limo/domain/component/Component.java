package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.event.Event;

/**
 * Base class for all main components of a supply chain: hubs, legs and events.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Component extends BaseEntity {
    
    protected List<Process> processes;
    protected List<Event> events;

    public Component() {
        super();
        this.processes = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
}
