package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.NotNull;
import nl.fontys.sofa.limo.validation.annotations.Null;


public class ObjectBean {
    
    @Null
    public Object nullObject;
    
    @NotNull
    public Object notNullObject;
}
