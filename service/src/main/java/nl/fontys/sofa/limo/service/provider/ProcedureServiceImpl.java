package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.ProcedureDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the ProcedureCategoryService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = ProcedureService.class)
public class ProcedureServiceImpl extends AbstractService<Procedure> implements ProcedureService {

    public ProcedureServiceImpl() throws DAONotFoundException {
        super(ProcedureDAO.class);
    }
}