package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.io.IOException;
import javax.imageio.ImageIO;
import nl.fontys.sofa.limo.domain.types.HubType;
import org.openide.nodes.BeanNode;
import org.openide.util.Exceptions;

/**
 * View representation of the HubType class. 
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends BeanNode{

	public HubTypeNode(HubType bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}

	@Override
	public Image getIcon(int type) {
		try {
			return ImageIO.read(getClass().getClassLoader().getResource("nl/fontys/sofa/limo/view/images/icons/hubtype01-24x24.png"));
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return super.getIcon(type);
	}
}
