package nl.fontys.sofa.limo.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.api.dao.HubDAO;
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
	private final HubDAO hubDAO;
	private final InstanceContent instanceContent;
	private final Lookup lookup;

	public HubServiceImpl() {
		hubDAO = Lookup.getDefault().lookup(HubDAO.class);
		instanceContent = new InstanceContent();
		lookup = new AbstractLookup(instanceContent);
		instanceContent.set(hubDAO.findAll(), null);
	}

	@Override
	public Hub findHubById(int id) {
		return hubDAO.findById(String.valueOf(id));
	}

	@Override
	public List<Hub> findAllHubs() {
		return hubDAO.findAll();
	}

	@Override
	public Hub insertHub(Hub hub) {
		Hub result = hubDAO.insert(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public boolean updateHub(Hub hub) {
		boolean result = hubDAO.update(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public boolean deleteHub(Hub hub) {
		boolean result = hubDAO.delete(hub);
		instanceContent.add(hub);
		return result;
	}

	@Override
	public Lookup getLookup() {
		return lookup;
	}

}
