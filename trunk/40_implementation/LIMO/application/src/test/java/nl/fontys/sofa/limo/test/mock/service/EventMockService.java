package nl.fontys.sofa.limo.test.mock.service;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import nl.fontys.sofa.limo.service.provider.AbstractService;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 * Mock the event service for using an in memory database.
 *
 * @author Sven Mäurer
 */
@ServiceProvider(service = EventService.class)
public class EventMockService extends AbstractService<Event> implements EventService {

    public EventMockService() throws DAONotFoundException {
        super(EventDAO.class);
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, "memory:tests");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
