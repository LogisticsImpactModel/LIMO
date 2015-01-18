package nl.fontys.sofa.limo.api.service.provider;

import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Events and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the data models.
 *
 * @author Sebastiaan Heijmann
 */
public interface EventService extends Lookup.Provider, DAO<Event> {
}
