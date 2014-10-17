package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 * View representation of Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubNode extends AbstractBeanNode{

	public HubNode(BaseEntity bean) throws IntrospectionException {
		super(bean);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}

}
