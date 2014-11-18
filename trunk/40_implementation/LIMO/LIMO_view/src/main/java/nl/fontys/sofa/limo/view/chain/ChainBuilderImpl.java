package nl.fontys.sofa.limo.view.chain;


import java.util.List;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * Implementation of the ChainBuilder interface.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainBuilderImpl implements ChainBuilder {

	@Override
	public boolean connectHubs(ConnectionHolder manager) {
		Hub source = manager.getSourceHub();
		Leg connection = manager.getConnectingLeg();
		Hub target = manager.getTargetHub();

		if (source != null && target != null && connection != null) {
			source.setNext(connection);
			connection.setPrevious(source);
			connection.setNext(target);
			target.setPrevious(connection);

			return true;
		}
		return false;
	}

	@Override
	public List<Hub> getAllHubs() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean validateChain() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
