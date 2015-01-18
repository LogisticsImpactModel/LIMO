package nl.fontys.sofa.limo.api.service.provider;

import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for HubTypes and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the data models.
 *
 * @author Sebastiaan Heijmann
 */
public interface HubTypeService extends Lookup.Provider, DAO<HubType> {
}
