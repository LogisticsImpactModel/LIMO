package nl.fontys.sofa.limo.domain.component.type;

import com.google.gson.annotations.Expose;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.Icon;

/**
 * A type is a template object for either a hub or a leg.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public abstract class Type extends Component {
    
    private static final long serialVersionUID = -2076965995663779924L;
    
    @Embedded
    @Expose protected Icon icon;
    
    public Type() {
        super();
    }
    
    public Type(Type type) {
        setName(type.getName());
        setDescription(type.getDescription());
        setEvents(type.getEvents());
        setIcon(type.getIcon());
        setProcedures(type.getProcedures());
    }
    
    public Icon getIcon() {
        return icon;
    }
    
    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
