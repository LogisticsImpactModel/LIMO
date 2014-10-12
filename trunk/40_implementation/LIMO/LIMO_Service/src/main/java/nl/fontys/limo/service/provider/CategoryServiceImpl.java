package nl.fontys.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.service.provider.CategoryService;
import nl.fontys.sofa.limo.domain.category.Category;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.apache.commons.collections.ListUtils;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the CostCategoryService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = CategoryService.class)
public class CategoryServiceImpl implements CategoryService{
	private final DAO costCategoryDAO;
	private final DAO timeCategoryDAO;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public CategoryServiceImpl() {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		costCategoryDAO = df.getCostCategoryDAO();
		timeCategoryDAO = df.getTimeCategoryDAO();
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		instanceContent.set(ListUtils.union(costCategoryDAO.findAll(),
				timeCategoryDAO.findAll()), null);
	}

	@Override
	public CostCategory findCostCategoryById(int id) {
		return (CostCategory) costCategoryDAO.findById(String.valueOf(id));
	}

	@Override
	public TimeCategory findTimeCategoryById(int id) {
		return (TimeCategory) timeCategoryDAO.findById(String.valueOf(id));
	}

	@Override
	public List<CostCategory> findAllCostCategories() {
		return costCategoryDAO.findAll();
	}

	@Override
	public List<TimeCategory> findAllTimeCategories() {
		return timeCategoryDAO.findAll();
	}

	@Override
	public List<Category> findAllCategories() {
		List<Category> categories = ListUtils.union(costCategoryDAO.findAll(),
				timeCategoryDAO.findAll());
		return categories;
	}

	@Override
	public CostCategory insertCostCategory(CostCategory cc) {
		CostCategory result = (CostCategory) costCategoryDAO.insert(cc);
		instanceContent.add(cc);
		return result;
	}

	@Override
	public TimeCategory insertTimeCategory(TimeCategory tc) {
		TimeCategory result = (TimeCategory) timeCategoryDAO.insert(tc);
		instanceContent.add(tc);
		return result;
	}

	@Override
	public boolean updateCostCategory(CostCategory cc) {
		boolean result = costCategoryDAO.update(cc);
		instanceContent.set(ListUtils.union(costCategoryDAO.findAll(),
				timeCategoryDAO.findAll()), null);
		return result;
	}

	@Override
	public boolean updateTimeCategory(TimeCategory tc) {
		boolean result = timeCategoryDAO.update(tc);
		instanceContent.set(ListUtils.union(costCategoryDAO.findAll(),
				timeCategoryDAO.findAll()), null);
		return result;
	}

	@Override
	public boolean deleteCostCategory(CostCategory cc) {
		boolean result = costCategoryDAO.delete(cc.getId());
		instanceContent.remove(cc);
		return result;
	}

	@Override
	public boolean deleteTimeCategory(TimeCategory tc) {
		boolean result = timeCategoryDAO.delete(tc.getId());
		instanceContent.remove(tc);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}
}
