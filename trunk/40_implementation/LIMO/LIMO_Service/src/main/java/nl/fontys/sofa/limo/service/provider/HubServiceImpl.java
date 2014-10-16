package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the HubService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = HubService.class)
public class HubServiceImpl extends AbstractService<Hub> implements HubService{

    public HubServiceImpl() throws DAONotFoundException {
        super(HubDAO.class);
    }
}
