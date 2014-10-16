package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.type.HubType;

/**
 * View representation of the HubType class. 
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends AbstractServiceNode{

	public HubTypeNode(HubType bean) throws IntrospectionException{
		super(bean);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}

}
