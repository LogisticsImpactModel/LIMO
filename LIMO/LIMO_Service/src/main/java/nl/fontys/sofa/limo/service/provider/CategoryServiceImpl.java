package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.ProcessCategoryDAO;
import nl.fontys.sofa.limo.api.service.provider.CategoryService;
import nl.fontys.sofa.limo.domain.component.process.ProcessCategory;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the CostCategoryService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = CategoryService.class)
public class CategoryServiceImpl implements CategoryService{
	private final ProcessCategoryDAO processCategoryDAO;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public CategoryServiceImpl() {
		processCategoryDAO = Lookup.getDefault().lookup(ProcessCategoryDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		instanceContent.set(processCategoryDAO.findAll(), null);
	}

	@Override
	public ProcessCategory findProcessCategoryById(int id) {
		return (ProcessCategory) processCategoryDAO.findById(String.valueOf(id));
	}

	@Override
	public List<ProcessCategory> findAllProcessCategories() {
		return processCategoryDAO.findAll();
	}

	@Override
	public ProcessCategory insertProcessCategory(ProcessCategory cc) {
		ProcessCategory result = (ProcessCategory) processCategoryDAO.insert(cc);
		instanceContent.add(cc);
		return result;
	}

	@Override
	public boolean updateProcessCategory(ProcessCategory cc) {
		boolean result = processCategoryDAO.update(cc);
		instanceContent.set(processCategoryDAO.findAll(), null);
		return result;
	}

	@Override
	public boolean deleteProcessCategory(ProcessCategory cc) {
		boolean result = processCategoryDAO.delete(cc);
		instanceContent.remove(cc);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}
}
