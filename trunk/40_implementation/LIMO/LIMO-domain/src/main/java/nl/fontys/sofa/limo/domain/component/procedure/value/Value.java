package nl.fontys.sofa.limo.domain.component.procedure.value;

import java.io.Serializable;

/**
 * Interface for a process' value.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public interface Value extends Serializable {

    double getValue();

    double getMin();

    double getMax();

}
