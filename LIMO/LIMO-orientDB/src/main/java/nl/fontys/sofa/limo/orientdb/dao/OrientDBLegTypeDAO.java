package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = LegTypeDAO.class)
public class OrientDBLegTypeDAO extends OrientDBAbstractDAO<LegType> implements LegTypeDAO {

    public OrientDBLegTypeDAO() {
        super(LegType.class);
    }
}
