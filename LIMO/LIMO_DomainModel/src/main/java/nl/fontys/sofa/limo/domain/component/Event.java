package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.distribution.DistributionType;

public class Event extends Component {

    private Component parent;
    private DistributionType probability;
    private EventExecutionStateDependency dependency;
    private boolean executionState;
    private Actor actor;

    public Event() {
        super();
    }

    @Override
    public void addEvent(Event event) {
        super.addEvent(event);
        event.setDependency(EventExecutionStateDependency.INDEPENDENT);
    }

    public void addEvent(Event event, EventExecutionStateDependency dependency) {
        super.addEvent(event);
        event.setDependency(dependency);
    }

    /**
     * Sets the execution state of this event and all subevents to false.
     */
    public void clearExecutionStates() {
        this.executionState = false;
        for (Event e : this.events) {
            e.clearExecutionStates();
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTER} ">
    public Event(String identifier) {
        super(identifier);
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public EventExecutionStateDependency getDependency() {
        return dependency;
    }

    public void setDependency(EventExecutionStateDependency dependency) {
        this.dependency = dependency;
    }

    public DistributionType getProbability() {
        return probability;
    }

    public void setProbability(DistributionType probability) {
        this.probability = probability;
    }

    public boolean isExecutionState() {
        return executionState;
    }

    public void setExecutionState(boolean executionState) {
        this.executionState = executionState;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
    // </editor-fold>

    @Override
    public Event copy() {
        Event copied = new Event();
        ArrayList<Entry> costsList = new ArrayList<>();
        for (Entry cost : costs) {
            costsList.add(cost.copy());
        }
        copied.setCosts(costsList);
        ArrayList<Entry> delaysList = new ArrayList<>();
        for (Entry delay : delays) {
            delaysList.add(delay.copy());
        }
        copied.setDelays(delaysList);
        copied.setDependency(dependency);
        copied.setDescription(description);
        copied.setExecutionState(executionState);
        copied.setIdentifier(identifier);
        copied.setLastUpdate(lastUpdate);
        ArrayList<Entry> leadTimesList = new ArrayList<>();
        for (Entry leadtime : leadTimes) {
            leadTimesList.add(leadtime.copy());
        }
        copied.setLeadTimes(leadTimesList);
        if (parent != null) {
            copied.setParent(parent.copy());
        }
        copied.setProbability(probability.copy());
        return copied;
    }

    @Override
    public String toString() {
        return this.identifier;
    }
}
