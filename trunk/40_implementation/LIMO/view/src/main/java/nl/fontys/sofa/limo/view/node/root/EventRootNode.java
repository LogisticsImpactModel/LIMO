package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

public class EventRootNode extends AbstractRootNode {

    public EventRootNode(Children children) /*throws ServiceNotFoundException*/ {
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
    public Action[] getActions(boolean context) {
        return new Action[]{SystemAction.get(NewAction.class)};
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
