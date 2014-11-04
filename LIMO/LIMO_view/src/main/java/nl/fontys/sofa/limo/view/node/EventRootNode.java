package nl.fontys.sofa.limo.view.node;

import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.nodes.Children;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class EventRootNode extends AbstractRootNode {

    public EventRootNode(Children children) throws ServiceNotFoundException {
        super(children);
    }

    @Override
    Class getServiceClass() {
        return EventService.class;
    }

    @Override
    Class getBeanClass() {
        return Event.class;
    }

}
