package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.openide.nodes.Children;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for event catalog.
 *
 * @author Sven Mäurer
 */
public class EventRootNode extends AbstractRootNode {

    public EventRootNode(Children children) {
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

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return "Event";
            }

            @Override
            public void create() throws IOException {
                EventWizardAction wiz = new EventWizardAction();
                wiz.actionPerformed(null);
            }
        }};
    }
}
