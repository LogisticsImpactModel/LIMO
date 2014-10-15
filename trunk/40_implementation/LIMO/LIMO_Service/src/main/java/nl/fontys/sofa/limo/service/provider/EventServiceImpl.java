package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the Eventservice interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = EventService.class)
public class EventServiceImpl implements EventService{
	private final EventDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public EventServiceImpl() throws DAONotFoundException {
		dao = Lookup.getDefault().lookup(EventDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		if(dao == null){
			throw new DAONotFoundException("EventDAO not found...");
		}else{
			instanceContent.set(dao.findAll(), null);
		}
	}

	@Override
	public Event findEventById(int id) {
		return dao.findById(String.valueOf(id));
	}

	@Override
	public List<Event> findAllEvents() {
		return dao.findAll();
	}

	@Override
	public Event insertEvent(Event event) {
		Event result = dao.insert(event);
		instanceContent.add(event);
		return result;
	}

	@Override
	public boolean updateEvent(Event event) {
		boolean result = dao.update(event);
		instanceContent.set(dao.findAll(), null);
		return result;
	}

	@Override
	public boolean deleteEvent(Event event) {
		boolean result = dao.delete(event);
		instanceContent.add(result);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}
}
