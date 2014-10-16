package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the HubTypeService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = HubTypeService.class)
public class HubTypeServiceImpl extends AbstractService<HubType> implements HubTypeService{

    public HubTypeServiceImpl() throws DAONotFoundException {
        super(HubTypeDAO.class);
    }
}
