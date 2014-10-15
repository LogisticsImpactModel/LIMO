package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.node.HubTypeNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory for creating the HubType children.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeChildFactory extends ChildFactory<HubType> 
		implements LookupListener{

	private final Result<HubType> lookupResult;
	private HubTypeService service; 

	public HubTypeChildFactory() {
		service = Lookup.getDefault().lookup(HubTypeService.class);
		lookupResult = service.getLookup().lookupResult(HubType.class);
		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<HubType> list) {
		list.addAll(lookupResult.allInstances());
		return true;
	}

	@Override
	protected Node createNodeForKey(HubType key) {
		BeanNode node = null;
		try {
			node = new HubTypeNode(key);
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
