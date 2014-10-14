package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Hubs and manages a
 * lookup. A LookupListener can be attached to the LookupResult to listen for
 * changes in the datamodels.

 *
 * @author Sebastiaan Heijmann
 */
public interface HubService extends Lookup.Provider{

	Hub findHubById(int id);

	List<Hub> findAllHubs();

	Hub insertHub(Hub hub);

	boolean updateHub(Hub hub);

	boolean deleteHub(Hub hub);

	@Override
	public Lookup getLookup();

}
