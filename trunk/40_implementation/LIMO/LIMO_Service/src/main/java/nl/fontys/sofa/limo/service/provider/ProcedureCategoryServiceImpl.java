package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the CostCategoryService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = ProcedureCategoryService.class)
public class ProcedureCategoryServiceImpl implements ProcedureCategoryService{
	private final ProcedureCategoryDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public ProcedureCategoryServiceImpl() throws DAONotFoundException {
		dao = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		if(dao == null){
			throw new DAONotFoundException("ProcedureCategoryDAO not found...");
		}else{
			instanceContent.set(dao.findAll(), null);
		}
	}

	@Override
	public ProcedureCategory findProcedureCategoryById(int id) {
		return (ProcedureCategory) dao.findById(String.valueOf(id));
	}

	@Override
	public List<ProcedureCategory> findAllProcedureCategories() {
		return dao.findAll();
	}

	@Override
	public ProcedureCategory insertProcedureCategory(ProcedureCategory procedure) {
		ProcedureCategory result = dao.insert(procedure);
		instanceContent.add(procedure);
		return result;
	}

	@Override
	public boolean updateProcedureCategory(ProcedureCategory procedure) {
		boolean result = dao.update(procedure);
		instanceContent.set(dao.findAll(), null);
		return result;
	}

	@Override
	public boolean deleteProcedureCategory(ProcedureCategory procedure) {
		boolean result = dao.delete(procedure);
		instanceContent.remove(procedure);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}
}
