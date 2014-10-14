package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for the and manages a lookup. A
 * LookupListener can be attached to the LookupResult to listen for changes in
 * the datamodels.
 *
 * @author Sebastiaan Heijmann
 */
public interface ProcedureService extends Lookup.Provider{

	ProcedureCategory findProcedureCategoryById(int id);

	List<ProcedureCategory> findAllProcedureCategories();

	ProcedureCategory insertProcedureCategory(ProcedureCategory pc);

	boolean updateProcedureCategory(ProcedureCategory pc);

	boolean deleteProcedureCategory(ProcedureCategory pc);

	@Override
	public Lookup getLookup();

}
