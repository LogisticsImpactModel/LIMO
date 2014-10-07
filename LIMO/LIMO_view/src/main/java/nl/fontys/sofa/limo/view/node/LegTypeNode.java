package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.types.LegType;
import org.openide.nodes.BeanNode;

/**
 * View representation of the LegType class. 
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeNode extends BeanNode{

	public LegTypeNode(LegType bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}

}
