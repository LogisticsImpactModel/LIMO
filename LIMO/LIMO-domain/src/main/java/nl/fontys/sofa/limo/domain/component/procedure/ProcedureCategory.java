package nl.fontys.sofa.limo.domain.component.procedure;

import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * Process category used to store possible categories in the database and
 * display them as a selection list for processes.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class ProcedureCategory extends BaseEntity {

    @Override
    public String toString() {
        return name;
    }
	
}
