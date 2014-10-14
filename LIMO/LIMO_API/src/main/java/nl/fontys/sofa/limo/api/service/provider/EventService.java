package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Events and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the datamodels.

 * @author Sebastiaan Heijmann
 */
public interface EventService extends Lookup.Provider{

	Event findEventById(int id);

	List<Event> findAllEvents();

	Event insertEvent(Event event);

	boolean updateEvent(Event event);

	boolean deleteEvent(Event event);

	@Override
	public Lookup getLookup();


}