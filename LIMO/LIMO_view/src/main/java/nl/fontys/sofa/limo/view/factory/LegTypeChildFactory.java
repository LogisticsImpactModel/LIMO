package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.api.service.provider.LegService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.LegTypeNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory for creating the leg type children.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends ChildFactory<LegType> 
		implements LookupListener{

	private final Result<LegType> lookupResult;
	private LegService service; 

	public LegTypeChildFactory() {
		service = Lookup.getDefault().lookup(LegService.class);
		lookupResult = service.getLookup().lookupResult(LegType.class);
		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<LegType> list) {
		list.addAll(lookupResult.allInstances());
		return true;
	}

	@Override
	protected Node createNodeForKey(LegType key) {
		BeanNode node = null;
		try {
			node = new LegTypeNode(key);
		} catch (IntrospectionException ex) {
			Exceptions.printStackTrace(ex);
		}
		return node;
	}

	@Override
	public void resultChanged(LookupEvent le) {
		refresh(true);
	}
}
