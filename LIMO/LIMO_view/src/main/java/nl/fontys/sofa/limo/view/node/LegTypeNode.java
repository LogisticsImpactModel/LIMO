package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.type.LegType;

/**
 * View representation of the LegType class. 
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeNode extends AbstractBeanNode{

	public LegTypeNode(LegType bean) throws IntrospectionException{
		super(bean);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}

}
