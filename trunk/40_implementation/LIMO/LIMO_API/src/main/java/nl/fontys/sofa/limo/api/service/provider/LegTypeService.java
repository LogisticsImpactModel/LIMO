package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for LegTypes and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the datamodels.
 *
 * @author Sebastiaan Heijmann
 */
public interface LegTypeService extends Lookup.Provider, DAO<LegType>{
}
