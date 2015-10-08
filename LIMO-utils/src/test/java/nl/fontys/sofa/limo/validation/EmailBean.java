package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.Pattern;

public class EmailBean {
    
    @Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$")
    public String email;
}
