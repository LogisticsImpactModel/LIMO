package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = EventDAO.class)
public class OrientDBEventDAO extends OrientDBAbstractDAO<Event> implements EventDAO {

    public OrientDBEventDAO() {
        super(Event.class);
    }

}
