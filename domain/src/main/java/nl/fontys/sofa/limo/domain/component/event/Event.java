package nl.fontys.sofa.limo.domain.component.event;

import com.google.gson.annotations.Expose;
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
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class Event extends Component {

    @Expose
    private static final long serialVersionUID = 4328629634718075652L;

    @Expose
    private ExecutionState dependency;
    @Embedded
    @Expose
    private Distribution probability;
    @Expose
    private ExecutionState executionState;

    public Event() {
        super();
    }

    public Event(Event event) {
        super(event);
        this.name = event.getName();
        this.description = getDescription();
        this.dependency = event.getDependency();
        this.probability = event.getProbability();
        this.executionState = event.getExecutionState();
        this.id = event.getId();
        this.uniqueIdentifier = event.getUniqueIdentifier();
        this.executionState = event.getExecutionState();
        this.dependency = event.getDependency();
    }

    public Event(String name, String description, ExecutionState dependency, Distribution probability, ExecutionState executionState) {
        super(name, description);
        this.dependency = dependency;
        this.probability = probability;
        this.executionState = executionState;
    }

    /**
     * Overwrites all attributes of the {@link Event}-object with the attributes
     * of the sourceEvent. The previous and next attributes are not copied.
     *
     * @param sourceEvent
     */
    public void deepOverwrite(Event sourceEvent) {
        setUniqueIdentifier(sourceEvent.getUniqueIdentifier());
        setId(sourceEvent.getId());
        setName(sourceEvent.getName());
        setProcedures(sourceEvent.getProcedures());
        setEvents(sourceEvent.getEvents());
        setDescription(sourceEvent.getDescription());
        setDependency(sourceEvent.getDependency());
        setProbability(sourceEvent.getProbability());
        setExecutionState(sourceEvent.getExecutionState());
    }

    public ExecutionState getDependency() {
        return dependency;
    }

    public void setDependency(ExecutionState dependency) {
        this.dependency = dependency;
        firePropertyChangeEvent();
    }

    public Distribution getProbability() {
        return probability;
    }

    public void setProbability(Distribution probability) {
        this.probability = probability;
        firePropertyChangeEvent();
    }

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState(ExecutionState executionState) {
        this.executionState = executionState;
        firePropertyChangeEvent();
    }
}
