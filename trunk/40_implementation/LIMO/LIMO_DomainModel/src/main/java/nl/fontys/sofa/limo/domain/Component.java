package nl.fontys.sofa.limo.domain;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Event;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Component extends BaseEntity {

    protected String identifier;
    protected ArrayList<Entry> costs;
    protected ArrayList<Entry> leadTimes;
    protected ArrayList<Entry> delays;
    protected ArrayList<Event> events;
    protected Icon icon;

    public Component() {

    }

    public Component(String identifier) {
        this(identifier, new ArrayList<Entry>(), new ArrayList<Entry>(), new ArrayList<Entry>(), new ArrayList<Event>(), new Icon());
    }

    public Component(String identifier, ArrayList<Entry> costs, ArrayList<Entry> leadTimes, ArrayList<Entry> delays, ArrayList<Event> events, byte[] icon) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.events = events;
        this.icon = new Icon(icon);
    }

    public Component(String identifier, ArrayList<Entry> costs, ArrayList<Entry> leadTimes, ArrayList<Entry> delays, ArrayList<Event> events, Image icon) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.events = events;
        this.icon = new Icon(icon);
    }

    public Component(String identifier, ArrayList<Entry> costs, ArrayList<Entry> leadTimes, ArrayList<Entry> delays, ArrayList<Event> events, String iconPath) {
        this.identifier = identifier;
        this.costs = costs;
        this.leadTimes = leadTimes;
        this.delays = delays;
        this.events = events;
        this.icon = new Icon(iconPath);
    }

    public Component(String identifier, ArrayList<Entry> costs, ArrayList<Entry> leadTimes, ArrayList<Entry> delays, ArrayList<Event> events, Icon icon) {
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

    public ArrayList<Entry> getCosts() {
        return costs;
    }

    public void setCosts(ArrayList<Entry> costs) {
        this.costs = costs;
    }

    public ArrayList<Entry> getLeadTimes() {
        return leadTimes;
    }

    public void setLeadTimes(ArrayList<Entry> leadTimes) {
        this.leadTimes = leadTimes;
    }

    public ArrayList<Entry> getDelays() {
        return delays;
    }

    public void setDelays(ArrayList<Entry> delays) {
        this.delays = delays;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /* EVENT ACCESSORS */
    /**
     * Returns an unmodifiable view of the events list.
     *
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
        for (Event e : this.events) {
            e.setParent(null);
        }

        this.events.clear();
    }

}
