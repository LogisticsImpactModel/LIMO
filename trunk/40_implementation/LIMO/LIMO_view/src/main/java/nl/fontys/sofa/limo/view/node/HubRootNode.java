package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionDialogInputPanel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for Hub.
 *
 * @author Sebastiaan Heijmann
 */
public class HubRootNode extends AbstractRootNode {

    public HubRootNode(Children children) throws ServiceNotFoundException {
        super(children);
    }

    @Override
    Class getServiceClass() {
        return HubService.class;
    }

    @Override
    Class getBeanClass() {
        return Hub.class;
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
                NameDescriptionDialogInputPanel inputPane = new NameDescriptionDialogInputPanel();
                DialogDescriptor dd = new DialogDescriptor(inputPane, "Hub");
                Object result = DialogDisplayer.getDefault().notify(dd);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (!name.isEmpty() && !description.isEmpty()) {
                    Hub h = new Hub();
                    h.setName(name);
                    h.setDescription(description);
                    service.insert(h);
                }
            }
        }};
    }

}
