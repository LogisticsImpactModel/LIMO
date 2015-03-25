package nl.fontys.sofa.limo.orientdb.dao;

import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = HubDAO.class)
public class OrientDBHubDAO extends OrientDBAbstractDAO<Hub> implements HubDAO {

    public OrientDBHubDAO() {
        super(Hub.class);
    }

}
