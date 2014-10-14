package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Implementation of the HubTypeService interface.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeServiceImpl implements HubTypeService{
	private final HubTypeDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public HubTypeServiceImpl() {
		dao = Lookup.getDefault().lookup(HubTypeDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);
		instanceContent.set(dao.findAll(), null);
	}

	@Override
	public HubType findHubTypeById(int id) {
		return dao.findById(String.valueOf(id));
	}

	@Override
	public List<HubType> findAllHubTypes() {
		return dao.findAll();
	}

	@Override
	public HubType insertHubType(HubType type) {
		HubType result = dao.insert(type);
		instanceContent.add(type);
		return result;
	}

	@Override
	public boolean updateHubType(HubType type) {
		boolean result = dao.update(type);
		instanceContent.add(result);
		return result;
	}

	@Override
	public boolean deleteHubType(HubType type) {
		boolean result = dao.delete(type);
		instanceContent.set(dao.findAll(), null);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}

}
