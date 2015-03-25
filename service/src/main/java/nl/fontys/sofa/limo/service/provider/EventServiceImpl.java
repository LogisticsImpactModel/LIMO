package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the Eventservice interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = EventService.class)
public class EventServiceImpl extends AbstractService<Event> implements EventService{

    public EventServiceImpl() throws DAONotFoundException {
        super(EventDAO.class);
    }
}
