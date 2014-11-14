package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.custom.pane.NameDescriptionDialogInputPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

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
    
    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{SystemAction.get(NewAction.class)};
    }
    
    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return "Hub";
            }

            @Override
            public void create() throws IOException {
                NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
                DialogDescriptor dd = new DialogDescriptor(inputPane, "Event");
                Object result = DialogDisplayer.getDefault().notify(dd);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (!name.isEmpty() && !description.isEmpty()) {
                    Event e = new Event();
                    e.setName(name);
                    e.setDescription(description);
                    service.insert(e);
                }
            }
        }};
    }
}
