package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import org.openide.actions.DeleteAction;
import org.openide.nodes.BeanNode;
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
	public Action[] getActions(boolean context) {
		return new Action[]{ (SystemAction.get(DeleteAction.class)) };
	}

	@Override
	public boolean canDestroy() {
		return true;
	}

	@Override
	public void destroy() throws IOException {
		fireNodeDestroyed();
	}

	
}
