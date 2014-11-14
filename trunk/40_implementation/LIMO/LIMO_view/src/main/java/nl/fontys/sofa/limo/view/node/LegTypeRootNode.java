package nl.fontys.sofa.limo.view.node;

import java.io.IOException;
import javax.swing.Action;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.pane.NameDescriptionDialogInputPane;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.actions.NewAction;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;

/**
 * Root node for LegType.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeRootNode extends AbstractRootNode {

    public LegTypeRootNode(Children children) throws ServiceNotFoundException {
        super(children);
    }

    @Override
    Class getBeanClass() {
        return LegType.class;
    }

    @Override
    Class getServiceClass() {
        return LegTypeService.class;
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
                return "Leg Type";
            }

            @Override
            public void create() throws IOException {
                NameDescriptionDialogInputPane inputPane = new NameDescriptionDialogInputPane();
                DialogDescriptor dd = new DialogDescriptor(inputPane, "Leg Type");
                DialogDisplayer.getDefault().notify(dd);

                String name = inputPane.getNameFieldValue();
                String description = inputPane.getDescriptionFieldValue();

                if (!name.isEmpty() && !description.isEmpty()) {
                    LegType lt = new LegType();
                    lt.setName(name);
                    lt.setDescription(description);

                    service.insert(lt);
                }
            }
        }};
    }
}
