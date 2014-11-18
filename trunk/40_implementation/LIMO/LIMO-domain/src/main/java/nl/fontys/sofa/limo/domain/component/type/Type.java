package nl.fontys.sofa.limo.domain.component.type;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 * A type is a template object for either a hub or a leg.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public abstract class Type extends BaseEntity {

    @Embedded
    protected List<Event> events;
    @Embedded
    protected List<Procedure> procedures;
    @Embedded
    protected Icon icon;

    public Type() {
        events = new ArrayList<>();
        procedures = new ArrayList<>();
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
