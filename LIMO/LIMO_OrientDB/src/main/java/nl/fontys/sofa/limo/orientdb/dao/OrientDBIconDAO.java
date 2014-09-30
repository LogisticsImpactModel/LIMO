package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.IconDAO;
import nl.fontys.sofa.limo.domain.Icon;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBIconDAO extends OrientDBAbstractDAO<Icon> implements IconDAO {

    public OrientDBIconDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, Icon.class);
    }

}
