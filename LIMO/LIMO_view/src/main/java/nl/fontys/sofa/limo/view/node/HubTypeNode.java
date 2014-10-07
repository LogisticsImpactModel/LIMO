package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.types.HubType;
import org.openide.nodes.BeanNode;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends BeanNode{

	public HubTypeNode(HubType bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}

}
