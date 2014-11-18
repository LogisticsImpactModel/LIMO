package nl.fontys.sofa.limo.domain.component.event;

import java.io.Serializable;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public enum ExecutionState implements Serializable {
    
    INDEPENDENT("Independent", "Execution is independent from parent component's execution state."),
    EXECUTED("Executed", "Execution only possible, if parent component was executed."),
    NOT_EXECUTED("Not executed", "Execution only possible, if parent component was not exeuted.");
    
    private final String name;
    private final String description;

    private ExecutionState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    public static ExecutionState stateFromBool(boolean isExecuted) {
        return isExecuted ? EXECUTED : NOT_EXECUTED;
    }
    
}
