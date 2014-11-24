package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.LegDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.LegService;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the LegService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = LegService.class)
public class LegServiceImpl extends AbstractService<Leg> implements LegService{

    public LegServiceImpl() throws DAONotFoundException {
        super(LegDAO.class);
    }
}
