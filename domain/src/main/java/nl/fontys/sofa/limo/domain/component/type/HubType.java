package nl.fontys.sofa.limo.domain.component.type;

/**
 * Template for a hub.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class HubType extends Type {

    private static final long serialVersionUID = -242677488690439113L;
    
    public HubType() {
    }

    public HubType(HubType type) {
        super(type);
        setId(type.getId());
    }

    
}
