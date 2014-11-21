package nl.fontys.sofa.limo.domain.component.event;

import java.util.Objects;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;

/**
 * An event inside a supply chain. An event's execution during simulation
 * depends on two things. The parent component and the execution dependency.
 * Event with Hub or Leg parents are always possible to be executed. Events with
 * an event parent, are only possible to be executed, if their parent's
 * execution state is equal to the event's execution dependency.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Event extends Component {

    private transient Component parent;
    private ExecutionState dependency;
    @Embedded
    private Distribution probability;
    private ExecutionState executionState;

    public Event() {
        super();
    }

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public ExecutionState getDependency() {
        return dependency;
    }

    public void setDependency(ExecutionState dependency) {
        this.dependency = dependency;
    }

    public Distribution getProbability() {
        return probability;
    }

    public void setProbability(Distribution probability) {
        this.probability = probability;
    }

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(ExecutionState executionState) {
        this.executionState = executionState;
    }

    @Override
    public boolean addEvent(Event event) {
        boolean canAdd = true;
        for (Event e : events) {//loop through events that are already in list
            if (e.getId().equals(event.getId())) { //..to make sure that the event that is to be added is not already in there
                canAdd = false;
                break;
            }
        }
        if (canAdd) {
            return events.add(event); //if the event to be added was not found in the list w/ existing events, add it
        }
        return false;
    }
}
