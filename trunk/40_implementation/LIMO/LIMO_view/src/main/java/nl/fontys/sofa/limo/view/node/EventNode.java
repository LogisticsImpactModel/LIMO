package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.BaseEntity;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class EventNode extends AbstractBeanNode{

	public EventNode(BaseEntity bean) throws IntrospectionException {
		super(bean);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}
}
