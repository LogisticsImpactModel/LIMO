package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.ProcedureCategoryDAO;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
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
@ServiceProvider(service = ProcedureService.class)
public class ProcedureServiceImpl implements ProcedureService{
	private final ProcedureCategoryDAO procedureCategoryDAO;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public ProcedureServiceImpl() {
		procedureCategoryDAO = Lookup.getDefault().lookup(ProcedureCategoryDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		instanceContent.set(procedureCategoryDAO.findAll(), null);
	}

	@Override
	public ProcedureCategory findProcedureCategoryById(int id) {
		return (ProcedureCategory) procedureCategoryDAO.findById(String.valueOf(id));
	}

	@Override
	public List<ProcedureCategory> findAllProcedureCategories() {
		return procedureCategoryDAO.findAll();
	}

	@Override
	public ProcedureCategory insertProcedureCategory(ProcedureCategory procedure) {
		ProcedureCategory result = procedureCategoryDAO.insert(procedure);
		instanceContent.add(procedure);
		return result;
	}

	@Override
	public boolean updateProcedureCategory(ProcedureCategory procedure) {
		boolean result = procedureCategoryDAO.update(procedure);
		instanceContent.set(procedureCategoryDAO.findAll(), null);
		return result;
	}

	@Override
	public boolean deleteProcedureCategory(ProcedureCategory procedure) {
		boolean result = procedureCategoryDAO.delete(procedure);
		instanceContent.remove(procedure);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}
}
