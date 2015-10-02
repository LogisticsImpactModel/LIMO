package nl.fontys.sofa.limo.domain.component.procedure.value;

import java.io.Serializable;

/**
 * Interface for a procedure's value.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public interface Value extends Serializable {

    double getValue();

    double getMin();

    double getMax();
}
