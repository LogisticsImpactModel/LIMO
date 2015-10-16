package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.AssertFalse;
import nl.fontys.sofa.limo.validation.annotations.AssertTrue;

public class BooleanBean {
    
    @AssertTrue
    boolean trueValue;
    
    @AssertFalse
    boolean falseValue;
}
