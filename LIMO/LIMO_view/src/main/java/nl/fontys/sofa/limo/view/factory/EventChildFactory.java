package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.node.EventNode;
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
 * Factory responsible for creating the Event children. It listens to changes in
 * the service layer and in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class EventChildFactory extends ChildFactory<Event>
        implements LookupListener, NodeListener {

    private final Result<Event> lookupResult;
    private final EventService service;
    private List<Event> eventList;

    public EventChildFactory() {
        service = Lookup.getDefault().lookup(EventService.class);
        lookupResult = service.getLookup().lookupResult(Event.class);
        lookupResult.addLookupListener(this);
    }

    public EventChildFactory(List<Event> events) {
        lookupResult = null;
        service = null;
        this.eventList = events;
    }

    @Override
    protected boolean createKeys(List<Event> list) {
        if (eventList == null) {
            list.addAll(lookupResult.allInstances());
        } else {
            list.addAll(eventList);
        }

        return true;
    }

    @Override
    protected Node createNodeForKey(Event key) {
        BeanNode node = null;
        try {
            node = new EventNode(key);
            node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent le) {
        refresh(true);
    }

    @Override
    public void nodeDestroyed(NodeEvent ne) {
        refresh(true);
    }

    @Override
    public void childrenAdded(NodeMemberEvent ev) {
    }

    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
    }

    @Override
    public void childrenReordered(NodeReorderEvent ev) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }

}
