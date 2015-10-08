package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.DecimalMax;
import nl.fontys.sofa.limo.validation.annotations.DecimalMin;

public class DecimalBean {
    
    @DecimalMin("1.0")
    public float minValue;
    
    @DecimalMax("10.0")
    public float maxValue;
}
