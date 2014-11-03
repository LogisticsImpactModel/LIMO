package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.actions.DeleteAction;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

/**
 * AbstractBeanNode class which defines basic Node actions and creates a lookup for
 the underlying bean.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractBeanNode extends BeanNode{
	InstanceContent ic;

	/**
	 * Abstract class which defines basic actions for all bean nodes and binds
	 * the datamodel.
	 * 
	 * @param bean the underlying datamodel
	 * @throws IntrospectionException 
	 */
	public AbstractBeanNode(BaseEntity bean) throws IntrospectionException{
		super(Lookups.singleton(bean), Children.LEAF);
		setShortDescription(bean.getDescription());
		setDisplayName(bean.getName());
	}

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(ProcedureCategory.class, type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}

	@Override
	public Action[] getActions(boolean context) {
		return new Action[]{SystemAction.get(DeleteAction.class)};
	}

	@Override
	public abstract boolean canDestroy(); 

	@Override
	public void destroy() throws IOException {
//		fireNodeDestroyed();
	}
}
