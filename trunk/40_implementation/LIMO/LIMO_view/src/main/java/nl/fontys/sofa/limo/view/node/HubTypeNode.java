package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.BeanNode;

/**
 * View representation of the HubType class. 
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends BeanNode{

	public HubTypeNode(HubType bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getName());
		setShortDescription(bean.getDescription());
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(HubType.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}
}
