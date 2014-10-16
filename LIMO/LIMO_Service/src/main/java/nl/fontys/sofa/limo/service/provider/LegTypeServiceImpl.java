package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the LegService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = LegTypeService.class)
public class LegTypeServiceImpl extends AbstractService<LegType> implements LegTypeService{

    public LegTypeServiceImpl() throws DAONotFoundException {
        super(LegTypeDAO.class);
    }

}
