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

/**
 * AbstractServiceNode class which defines basic Node actions and creates a lookup for
 * the underlying bean.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractServiceNode extends BeanNode{
	InstanceContent ic;

	/**
	 * Constructor requires a DAO implementation and an underlying datamodel.
	 * @param service the service implementation
	 * @param bean the underlying datamodel
	 * @throws IntrospectionException 
	 */
	public AbstractServiceNode(BaseEntity bean) throws IntrospectionException{
		this(bean, new InstanceContent());
	}

	private AbstractServiceNode(BaseEntity bean, InstanceContent ic)
			throws IntrospectionException{
		super(bean, Children.LEAF, new AbstractLookup(ic));
		this.ic = ic;
		ic.add(bean);

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
		fireNodeDestroyed();
	}
}
