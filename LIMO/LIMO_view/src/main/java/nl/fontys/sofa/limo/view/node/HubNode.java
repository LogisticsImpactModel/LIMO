package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode{

	public HubNode(BaseEntity bean) throws IntrospectionException {
		super(bean, Hub.class);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}

}
