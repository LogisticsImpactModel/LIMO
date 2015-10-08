package nl.fontys.sofa.limo.validation;

import java.util.Date;
import nl.fontys.sofa.limo.validation.annotations.Future;
import nl.fontys.sofa.limo.validation.annotations.Past;


public class DateBean {
    
    @Past
    public Date pastDate;
    
    @Future
    public Date futureDate;
}
