package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubDAO;
import nl.fontys.sofa.limo.api.exception.DAONotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the HubService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = HubService.class)
public class HubServiceImpl implements HubService{
	private final HubDAO dao;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public HubServiceImpl() throws DAONotFoundException {
		dao = Lookup.getDefault().lookup(HubDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);

		if(dao == null){
			throw new DAONotFoundException("HubDAO not found...");
		}else{
			instanceContent.set(dao.findAll(), null);
		}
	}

	@Override
	public Hub findHubById(int id) {
		return dao.findById(String.valueOf(id));
	}

	@Override
	public List<Hub> findAllHubs() {
		return dao.findAll();
	}

	@Override
	public Hub insertHub(Hub hub) {
		Hub result = dao.insert(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public boolean updateHub(Hub hub) {
		boolean result = dao.update(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public boolean deleteHub(Hub hub) {
		boolean result = dao.delete(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}

}
