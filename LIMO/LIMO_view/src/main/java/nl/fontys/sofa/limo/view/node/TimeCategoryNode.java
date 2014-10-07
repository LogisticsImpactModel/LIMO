package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.openide.actions.DeleteAction;
import org.openide.nodes.BeanNode;
import org.openide.util.Exceptions;
import org.openide.util.actions.SystemAction;

/**
 * View representation of the TimeCategory class.
 * 
 * @author Sebastiaan Heijmann
 */
public class TimeCategoryNode extends BeanNode{

	public TimeCategoryNode(TimeCategory bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}

	@Override
	public Image getIcon(int type) {
		try {
			return ImageIO.read(getClass().getClassLoader().getResource("nl/fontys/sofa/limo/view/images/icons/time01-24x24.png"));
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return super.getIcon(type);
	}
	
}
