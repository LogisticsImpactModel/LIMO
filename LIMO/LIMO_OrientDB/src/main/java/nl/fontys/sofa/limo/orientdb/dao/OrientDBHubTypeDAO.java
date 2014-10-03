package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.domain.types.HubType;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBHubTypeDAO extends OrientDBAbstractDAO<HubType> implements HubTypeDAO {

    public OrientDBHubTypeDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, HubType.class);
    }

}
