package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;

/**
 * View representation of the CostCategory class. 
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryNode extends AbstractBeanNode{

	public ProcedureCategoryNode(ProcedureCategory bean) 
			throws IntrospectionException{
		super(bean, ProcedureCategory.class);
	}

	@Override
	public boolean canDestroy() {
		return true;
	}
}
