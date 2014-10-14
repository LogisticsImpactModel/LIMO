package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for the categories (Cost and Time 
 * categories) and manages a lookup. A LookupListener can be attached to the
 * LookupResult to listen for changes in the datamodels.
 *
 * @author Sebastiaan Heijmann
 */
public interface CategoryService extends Lookup.Provider{

	ProcedureCategory findProcessCategoryById(int id);

	List<ProcedureCategory> findAllProcessCategories();

	ProcedureCategory insertProcessCategory(ProcedureCategory pc);

	boolean updateProcessCategory(ProcedureCategory pc);

	boolean deleteProcessCategory(ProcedureCategory pc);

	@Override
	public Lookup getLookup();

}
