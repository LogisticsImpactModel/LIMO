package nl.fontys.sofa.limo.domain.component.event;

import java.io.Serializable;

/**
 * Execution state used for events and sub-event dependencies.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public enum ExecutionState implements Serializable {

    /**
     * Sub-events can happen independent of their parent's execution state.
     */
    INDEPENDENT("Independent", "Execution is independent from parent component's execution state."),
    /**
     * Event was executed. Sub-events with this dependency can only execute, if
     * their parent executed.
     */
    EXECUTED("Executed", "Execution only possible, if parent component was executed."),
    /**
     * Event was not executed. Sub-events with this dependency can only execute,
     * if their parent was not executed.
     */
    NOT_EXECUTED("Not executed", "Execution only possible, if parent component was not exeuted.");

    /**
     * Name of the execution state.
     */
    private final String name;

    /**
     * Description for the execution state.
     */
    private final String description;

    /**
     * Create execution state.
     *
     * @param name Name.
     * @param description Description.
     */
    private ExecutionState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Name of the execution state.
     *
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Description of the execution state.
     *
     * @return Description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * For ease of use in tables, combo boxes, etc.
     *
     * @return Name.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Get execution state from a boolean value.
     *
     * @param isExecuted Boolean.
     * @return EXECUTED if true, NOT_EXECUTED if false.
     */
    public static ExecutionState stateFromBool(boolean isExecuted) {
        return isExecuted ? EXECUTED : NOT_EXECUTED;
    }

}
