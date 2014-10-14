package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = HubTypeDAO.class)
public class OrientDBHubTypeDAO extends OrientDBAbstractDAO<HubType> implements HubTypeDAO {

    public OrientDBHubTypeDAO() {
        super(HubType.class);
    }

}
