package nl.fontys.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import org.openide.nodes.BeanNode;

/**
 * View representation of the CostCategory class. 
 *
 * @author Sebastiaan Heijmann
 */
public class CostCategoryNode extends BeanNode{

	public CostCategoryNode(CostCategory bean) throws IntrospectionException {
		super(bean);
		setDisplayName(bean.getIdentifier());
		setShortDescription(bean.getDescription());
	}
	

}
