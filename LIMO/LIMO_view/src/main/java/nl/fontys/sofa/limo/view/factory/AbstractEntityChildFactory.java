package nl.fontys.sofa.limo.view.factory;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Sebastiaan Heijmann
 */
abstract class AbstractEntityChildFactory<T extends BaseEntity>
		extends ChildFactory<T> implements LookupListener, NodeListener{

	private final Result<T> lookupResult;
	private final Lookup.Provider lookupProvider; 

	public AbstractEntityChildFactory() {
		lookupProvider = (Lookup.Provider) Lookup.getDefault().lookup(getServiceClass());
		lookupResult = lookupProvider.getLookup().lookupResult(getBeanClass());
		lookupResult.addLookupListener(this);
	}

	abstract Class getServiceClass();

	abstract Class getBeanClass();

	abstract Class getNodeClass();
	
	@Override
	protected boolean createKeys(List<T> list) {
		DAO serviceProvider = (DAO) lookupProvider;
		Collection<? extends T> tcl = serviceProvider.findAll();
		list.addAll(tcl);
		return true;
	}

    @Override
    protected Node createNodeForKey(T key) {
        BeanNode node = null;
		try {
			node = (BeanNode) getNodeClass().getDeclaredConstructor(BaseEntity.class).newInstance(key);
//			node = (BeanNode) getNodeClass().newInstance();
		} catch (InstantiationException | IllegalAccessException |
				NoSuchMethodException | SecurityException |
				IllegalArgumentException | InvocationTargetException ex) {
			Exceptions.printStackTrace(ex);
		}
		if(node != null){node.addNodeListener(this);}
        return node;
    }

	@Override
	public void resultChanged(LookupEvent ev) {
	}

	@Override
	public void childrenAdded(NodeMemberEvent ev) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void childrenRemoved(NodeMemberEvent ev) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void nodeDestroyed(NodeEvent ne) {
		DAO serviceProvider = (DAO) lookupProvider;

		Node node = ne.getNode();
		T t = (T) node.getLookup().lookup(getBeanClass());
		serviceProvider.delete(t);
		refresh(true);
	}

	@Override
	public void childrenReordered(NodeReorderEvent ev) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
	}

}
