package nl.fontys.sofa.limo.test.mock.service;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.orientdb.OrientDBConnector;
import nl.fontys.sofa.limo.service.provider.AbstractService;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;

/**
 * Mock the hub service for using an in memory database.
 *
 * @author Sven Mäurer
 */
@ServiceProvider(service = HubService.class)
public class HubMockService extends AbstractService<Hub> implements HubService {

    public HubMockService() throws DAONotFoundException {
        super(HubDAO.class);
        try {
            Field databaseURLField = OrientDBConnector.class.getDeclaredField("databaseURL");
            databaseURLField.setAccessible(true);
            databaseURLField.set(null, "memory:tests");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
