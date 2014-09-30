package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.component.Leg;
import nl.fontys.sofa.limo.orientdb.database.OrientDBAccess;

public class OrientDBLegDAO extends OrientDBAbstractDAO<Leg> implements LegDAO {

    public OrientDBLegDAO(OrientDBAccess orientDBAccess) {
        super(orientDBAccess, Leg.class);
    }
}
