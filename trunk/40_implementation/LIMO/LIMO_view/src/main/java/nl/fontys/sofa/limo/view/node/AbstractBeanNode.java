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
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;

/**
 * AbstractBeanNode class which defines basic Node actions and creates a lookup for
 the underlying bean.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractBeanNode extends BeanNode{

	/**
 	 * Abstract class which defines basic implementations for nodes and binds
	 * the datamodel. The datamodel is available through the AbstractBeanNode's 
	 * lookup. <p> Override getActions and getNewTypes methods to define
	 * actions associated with this Node.
	 * 
	 * @param bean the underlying datamodel
	 * @throws IntrospectionException 
	 */
	public AbstractBeanNode(BaseEntity bean) throws IntrospectionException{
		this(bean, new InstanceContent());
	}

	private AbstractBeanNode(BaseEntity bean, InstanceContent ic) throws IntrospectionException{
		super(Lookups.singleton(bean), Children.LEAF, new AbstractLookup(ic));
		ic.add(bean);
		String description = bean.getDescription();
		String name = bean.getName();
		setShortDescription(description);
		setDisplayName(name);
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
		fireNodeDestroyed();
	}
	
}
