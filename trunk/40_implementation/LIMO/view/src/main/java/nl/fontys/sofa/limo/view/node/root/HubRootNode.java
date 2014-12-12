package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.openide.nodes.Children;
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
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return LIMOResourceBundle.getString("HUB");
            }

            @Override
            public void create() throws IOException {
                HubWizardAction wiz = new HubWizardAction();
                wiz.actionPerformed(null);
            }
        }};
    }

}
