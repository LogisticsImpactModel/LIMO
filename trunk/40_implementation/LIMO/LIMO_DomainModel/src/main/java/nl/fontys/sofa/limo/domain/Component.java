package nl.fontys.sofa.limo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.fontys.sofa.limo.domain.events.Event;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Component {
    
    protected String identifier;
    protected List<Entry> costs;
    protected List<Entry> leadTimes;
    protected List<Entry> delays;
    protected List<Event> events;
    
    /**
     * Actual bytes the icon is composed of.
     */
    protected byte[] icon;

    public Component(String identifier) {
        this(identifier, new ArrayList<Entry>(), new ArrayList<Entry>(), new ArrayList<Entry>(), new ArrayList<Event>(), new byte[]{});
    }

    public Component(String identifier, List<Entry> costs, List<Entry> leadTimes, List<Entry> delays, List<Event> events, byte[] icon) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.events = events;
        this.icon = icon;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Entry> getCosts() {
        return costs;
    }

    public void setCosts(List<Entry> costs) {
        this.costs = costs;
    }

    public List<Entry> getLeadTimes() {
        return leadTimes;
    }

    public void setLeadTimes(List<Entry> leadTimes) {
        this.leadTimes = leadTimes;
    }

    public List<Entry> getDelays() {
        return delays;
    }

    public void setDelays(List<Entry> delays) {
        this.delays = delays;
    }
    
    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
    
    /* EVENT ACCESSORS */

    /**
     * Returns an unmodifiable view of the events list.
     * @return Unmodifiable list of events.
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(this.events);
    }
    
    public void addEvent(Event event) {
        this.events.add(event);
        event.setParent(this);
    }
    
    public void removeEvent(Event event) {
        this.events.remove(event);
        event.setParent(null);
    }
    
    public void clearEvents() {
        for (Event e : this.events)
            e.setParent(null);
        
        this.events.clear();
    }
    
}
