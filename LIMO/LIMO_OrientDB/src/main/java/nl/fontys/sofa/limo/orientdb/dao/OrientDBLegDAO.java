package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LegDAO.class)
public class OrientDBLegDAO extends OrientDBAbstractDAO<Leg> implements LegDAO {

    public OrientDBLegDAO() {
        super(Leg.class);
    }
}
