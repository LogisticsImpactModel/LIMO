package nl.fontys.sofa.limo.domain.component.type;

import java.util.List;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * A type is a template object for either a hub or a leg.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Type extends BaseEntity{
    
    @Embedded
    protected List<Process> processes;

    public Type() {
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }
    
}
