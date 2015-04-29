package nl.fontys.sofa.limo.domain.component.procedure;

import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * ProcedureCategory is used to store possible categories of procedures in the
 * database and use them to group related procedures.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
public class ProcedureCategory extends BaseEntity {

    @Override
    public String toString() {
        return name;
    }

}
