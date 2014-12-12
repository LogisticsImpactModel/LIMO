package nl.fontys.sofa.limo.view.node.root;

import java.io.IOException;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.hub.HubTypeWizardAction;
import org.openide.nodes.Children;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeRootNode extends AbstractRootNode {

    public HubTypeRootNode(Children children) throws ServiceNotFoundException {
        super(children);
    }

    @Override
    Class getBeanClass() {
        return HubType.class;
    }

    @Override
    Class getServiceClass() {
        return HubTypeService.class;
    }

    @Override
    public NewType[] getNewTypes() {
        return new NewType[]{new NewType() {

            @Override
            public String getName() {
                return LIMOResourceBundle.getString("HUB_TYPE");
            }

            @Override
            public void create() throws IOException {
                HubTypeWizardAction wiz = new HubTypeWizardAction();
                wiz.actionPerformed(null);
            }
        }};
    }
}
