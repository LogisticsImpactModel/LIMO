package nl.fontys.sofa.limo.domain.value;

import java.io.Serializable;
import nl.fontys.sofa.limo.domain.interfaces.Copyable;

public interface Value extends Serializable, Copyable<Value> {

    double getValue();

    double getMin();

    double getMax();

}
