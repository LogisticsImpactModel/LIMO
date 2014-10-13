package nl.fontys.sofa.limo.api.service.provider;

import java.util.List;
import nl.fontys.sofa.limo.domain.component.Hub;
import nl.fontys.sofa.limo.domain.types.HubType;
import org.openide.util.Lookup;

/**
 * Interface which defines service methods for Hubs and Hubtypes and manages a
 * lookup.
 *
 * @author Sebastiaan Heijmann
 */
public interface HubService extends Lookup.Provider{

	Hub findHubById(int id);

	HubType findHubTypeById(int id);

	List<Hub> findAllHubs();

	List<HubType> findAllHubTypes();

	Hub insertHub(Hub hub);

	HubType insertHubType(HubType type);

	boolean updateHub(Hub hub);

	boolean updateHubType(HubType type);

	boolean deleteHub(Hub hub);

	boolean deleteHubType(HubType type);

	@Override
	public Lookup getLookup();

}
