package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.EventDAO;
import nl.fontys.sofa.limo.domain.component.Event;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBEventDAO extends OrientDBAbstractDAO<Event> implements EventDAO {

    public OrientDBEventDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, Event.class);
    }

}
