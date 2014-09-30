package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.types.LegType;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBLegTypeDAO extends OrientDBAbstractDAO<LegType> implements LegTypeDAO {

    public OrientDBLegTypeDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, LegType.class);
    }
}
