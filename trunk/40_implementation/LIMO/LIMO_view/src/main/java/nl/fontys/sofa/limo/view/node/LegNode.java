package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * View representation of a Leg.
 *
 * @author Sebastiaan Heijmann
 */
public class LegNode extends AbstractBeanNode<Leg>{
	private final Leg bean;

	public LegNode(Leg bean) throws IntrospectionException {
		super(bean, Leg.class);
		this.bean = bean;
	}

	@Override
	public boolean canDestroy() {
		return false;
	}

}
