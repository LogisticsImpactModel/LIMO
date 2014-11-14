package nl.fontys.sofa.limo.view.chain;


import java.util.List;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * ChainService provides methods to build a chain and to validate a given chain.
 *
 * @author Sebastiaan Heijmann
 */
public interface ChainService {

	/**
	 * Connect two Hubs together through a Leg.
	 * @param manager - the connection manager which holds the two Hubs and a
	 * connecting Leg.
	 * @return boolean - true if successfully connected.
	 */
	boolean connectHubs(ConnectionManager manager);

	/**
	 * Get all the hubs currently in the chain.
	 * @return 
	 */
	List<Hub> getAllHubs();

	/**
	 * Validate the current chain. <p> All the hubs must be connected via Legs
	 * for validation to succeed.
	 * @return boolean - true if validation is successful.
	 */
	boolean validateChain();

}
