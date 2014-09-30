package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBHubDAO extends OrientDBAbstractDAO<Hub> implements HubDAO {

    public OrientDBHubDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, Hub.class);
    }

}
