package nl.fontys.sofa.limo.domain.component;

import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.distribution.DistributionType;

public class Event extends Component {

    private Component parent;
    private DistributionType probability;
    private EventExecutionStateDependency dependency;
    private boolean executionState;
    private transient Actor actor;

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
}
