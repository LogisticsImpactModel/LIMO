package nl.fontys.sofa.limo.view.chain;


import java.util.List;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of the ChainService interface.
 *
 * @author Sebastiaan Heijmann
 */
@ServiceProvider(service = ChainService.class)
public class ChainServiceImpl implements ChainService {

	@Override
	public boolean connectHubs(ConnectionManager manager) {
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
