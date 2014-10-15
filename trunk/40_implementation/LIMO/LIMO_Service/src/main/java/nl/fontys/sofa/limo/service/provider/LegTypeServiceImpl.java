package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the LegService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = LegTypeService.class)
public class LegTypeServiceImpl implements LegTypeService{
	private final LegTypeDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public LegTypeServiceImpl() throws DAONotFoundException {
		dao = Lookup.getDefault().lookup(LegTypeDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		if(dao == null){
			throw new DAONotFoundException("LegTypeDAO not found...");
		}else{
			instanceContent.set(dao.findAll(), null);
		}
	}

	@Override
	public LegType findLegTypeById(int id) {
		return dao.findById(String.valueOf(id));
	}

	@Override
	public List<LegType> findAllLegTypes() {
		return dao.findAll();
	}

	@Override
	public LegType insertLegType(LegType type) {
		LegType result = dao.insert(type);
		instanceContent.add(type);
		return result;
	}

	@Override
	public boolean updateLegType(LegType type) {
		boolean result = dao.update(type);
		instanceContent.add(result);
		return result;
	}

	@Override
	public boolean deleteLegType(LegType type) {
		boolean result = dao.delete(type);
		instanceContent.set(dao.findAll(), null);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}

}
