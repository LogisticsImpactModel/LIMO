package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;

/**
 * Abstract class which defines basic implementations for root nodes. Override
 * getActions and getNewTypes methods to define actions associated with this Node.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractRootNode extends AbstractNode{
	protected DAO service;
	
	public AbstractRootNode(Children children) throws ServiceNotFoundException {
		super(children);
		service = (DAO) Lookup.getDefault().lookup(getServiceClass());
		if (service == null){throw new ServiceNotFoundException(getServiceClass().getSimpleName()
				+ " not found...");}
	}

	abstract Class getServiceClass();

	abstract Class getBeanClass();

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(getBeanClass(), type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}

    public DAO getService() {
        return service;
    }
}

