package nl.fontys.sofa.limo.domain.component.type;

import java.util.ArrayList;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.Icon;

/**
 * A type is a template object for either a hub or a leg.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Type extends Component {

    @Embedded
    protected Icon icon;

    public Type() {
        events = new ArrayList<>();
        procedures = new ArrayList<>();
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
