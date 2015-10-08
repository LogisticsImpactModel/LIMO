package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.Max;
import nl.fontys.sofa.limo.validation.annotations.Min;

public class IntegerBean {
    
    @Min(0)
    public int minValue;
    
    @Max(10)
    public int maxValue;
}
