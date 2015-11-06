package nl.fontys.sofa.limo.api.service.provider;

import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for the and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the data models.
 *
 * @author Sebastiaan Heijmann
 */
public interface ProcedureService extends DAO<Procedure>, Lookup.Provider {
}
