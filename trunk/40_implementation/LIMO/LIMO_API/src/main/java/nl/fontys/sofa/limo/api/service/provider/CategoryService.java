package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.category.Category;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for the categories (Cost and Time 
 * categories) and manages a lookup.
 *
 * @author Sebastiaan Heijmann
 */
public interface CategoryService extends Lookup.Provider{

	CostCategory findCostCategoryById(int id);

	TimeCategory findTimeCategoryById(int id);

	List<CostCategory> findAllCostCategories();

	List<TimeCategory> findAllTimeCategories();

	List<Category> findAllCategories();

	CostCategory insertCostCategory(CostCategory cc);

	TimeCategory insertTimeCategory(TimeCategory tc);

	boolean updateCostCategory(CostCategory cc);

	boolean updateTimeCategory(TimeCategory tc);

	boolean deleteCostCategory(CostCategory cc);

	boolean deleteTimeCategory(TimeCategory cc);

	@Override
	public Lookup getLookup();

}
