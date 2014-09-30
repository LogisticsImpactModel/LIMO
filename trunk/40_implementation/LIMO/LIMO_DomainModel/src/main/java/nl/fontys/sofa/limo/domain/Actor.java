package nl.fontys.sofa.limo.domain;

import java.io.Serializable;
import nl.fontys.sofa.limo.domain.component.Leg;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Actor implements Serializable {

    private final String name;
    private final List<Leg> legsResponsibleFor;

    public Actor(String name) {
        this.name = name;
        legsResponsibleFor = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Leg> getLegsResponsibleFor() {
        return this.legsResponsibleFor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
