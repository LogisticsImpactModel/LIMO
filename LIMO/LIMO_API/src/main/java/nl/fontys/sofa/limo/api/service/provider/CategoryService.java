package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.process.ProcessCategory;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for the categories (Cost and Time 
 * categories) and manages a lookup.
 *
 * @author Sebastiaan Heijmann
 */
public interface CategoryService extends Lookup.Provider{

	ProcessCategory findProcessCategoryById(int id);

	List<ProcessCategory> findAllProcessCategories();

	ProcessCategory insertProcessCategory(ProcessCategory pc);

	boolean updateProcessCategory(ProcessCategory pc);

	boolean deleteProcessCategory(ProcessCategory pc);

	@Override
	public Lookup getLookup();

}
