package nl.fontys.sofa.limo.validation;

import nl.fontys.sofa.limo.validation.annotations.Pattern;

public class EmailBean {
    
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    String email;
}
