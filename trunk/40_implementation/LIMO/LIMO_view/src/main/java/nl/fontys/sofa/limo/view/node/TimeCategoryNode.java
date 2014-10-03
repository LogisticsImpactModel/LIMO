package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.openide.nodes.BeanNode;

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

}
