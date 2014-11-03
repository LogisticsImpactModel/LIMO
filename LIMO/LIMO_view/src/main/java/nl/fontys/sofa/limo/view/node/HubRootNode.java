package nl.fontys.sofa.limo.view.node;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.nodes.Children;

/**
 * Root node for Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubRootNode extends AbstractRootNode{

	public HubRootNode(Children children) throws ServiceNotFoundException {
		super(children);
	}

	@Override
	Class getServiceClass() {
		return HubService.class;
	}

	@Override
	Class getBeanClass() {
		return Hub.class;
	}

}
