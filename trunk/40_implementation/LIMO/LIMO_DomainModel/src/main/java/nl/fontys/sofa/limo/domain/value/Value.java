package nl.fontys.sofa.limo.domain.value;

import java.io.Serializable;

/**
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public interface Value extends Serializable {

    double getValue();

    double getMin();

    double getMax();

}
