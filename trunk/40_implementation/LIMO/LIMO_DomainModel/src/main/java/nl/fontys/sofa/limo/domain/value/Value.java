package nl.fontys.sofa.limo.domain.value;

import java.io.Serializable;

public interface Value extends Serializable {

    double getValue();

    double getMin();

    double getMax();

}
