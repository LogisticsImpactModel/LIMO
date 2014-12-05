package nl.fontys.sofa.limo.service.provider;

import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the CostCategoryService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = ProcedureCategoryService.class)
public class ProcedureCategoryServiceImpl extends AbstractService<ProcedureCategory> implements ProcedureCategoryService{

    public ProcedureCategoryServiceImpl() throws DAONotFoundException {
        super(ProcedureCategoryDAO.class);
    }
	
}
