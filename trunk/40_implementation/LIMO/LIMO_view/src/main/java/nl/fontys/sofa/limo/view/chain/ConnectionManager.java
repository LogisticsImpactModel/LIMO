package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * ConnectionManager provides methods to create a connection between two hubs. A
 * connection consists of a source hub, a connecting leg and a target hub.
 * 
 * @author Sebastiaan Heijmann
 */
public interface ConnectionManager {

	/**
	 * Get the source Hub.
	 * @return Hub - the source hub.
	 */
	Hub getSourceHub();

	/**
	 * Get the Leg that connects the two Hubs.
	 * @return Leg - the connecting Leg.
	 */
	Leg getConnectingLeg();

	/**
	 * Get the target Hub.
	 * @return Hub - the target Hub.
	 */
	Hub getTargetHub();

	/**
	 * Set the source Hub.
	 * @param source - the source Hub.
	 */
	void addSourceHub(Hub source);

	/**
	 * Set the Leg that connects the two Hubs.
	 * @param connection - the connecting Leg.
	 */
	void addConnectingLeg(Leg connection);

	/**
	 * Set the target Hub.
	 * @param target - the target Hub.
	 */
	void addTargetHub(Hub target);


}
