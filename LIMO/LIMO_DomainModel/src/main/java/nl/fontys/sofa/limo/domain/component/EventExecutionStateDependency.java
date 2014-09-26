package nl.fontys.sofa.limo.domain.component;

import java.io.Serializable;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public enum EventExecutionStateDependency implements Serializable{
    
    INDEPENDENT,
    EXECUTED,
    NOT_EXECUTED;
    
}
