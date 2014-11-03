package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.node.EventNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class EventChildFactory extends ChildFactory<Event>
		implements LookupListener{

	private final Result<Event> lookupResult;
	private final EventService service; 

	public EventChildFactory() {
		service = Lookup.getDefault().lookup(EventService.class);
		lookupResult = service.getLookup().lookupResult(Event.class);
		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<Event> list) {
		list.addAll(lookupResult.allInstances());
		return true;
	}

	@Override
	protected Node createNodeForKey(Event key) {
		BeanNode node = null;
		try {
			node = new EventNode(key);
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
