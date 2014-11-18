package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * ConnectionHolderImpl is responsible for managing connections between two hubs
 * by using a leg.
 * 
 * @author Sebastiaan Heijmann
 */
public class ConnectionHolderImpl implements ConnectionHolder{

	private Hub sourceHub;
	private Leg leg;
	private Hub targetHub;

	@Override
	public Hub getSourceHub() {
		return sourceHub;
	}

	@Override
	public Leg getConnectingLeg() {
		return leg;
	}

	@Override
	public Hub getTargetHub() {
		return targetHub;
	}

	@Override
	public void addSourceHub(Hub source) {
		this.sourceHub = source;
	}

	@Override
	public void addConnectingLeg(Leg connection) {
		this.leg = connection;
	}

	@Override
	public void addTargetHub(Hub target) {
		this.targetHub = target;
	}
}
